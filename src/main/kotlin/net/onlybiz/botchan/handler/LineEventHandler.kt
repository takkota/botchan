package net.onlybiz.botchan.api

import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.action.URIAction
import com.linecorp.bot.model.event.*
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.event.link.LinkContent
import com.linecorp.bot.model.message.TemplateMessage
import com.linecorp.bot.model.message.template.ButtonsTemplate
import net.onlybiz.botchan.database.*
import net.onlybiz.botchan.model.line.response.LinkToken
import net.onlybiz.botchan.service.UserService
import net.onlybiz.botchan.settings.DeepLink
import net.onlybiz.botchan.settings.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@LineMessageHandler
class LineEventHandler {

    @Autowired
    lateinit var restOperations: RestOperations

    @Autowired
    lateinit var deeplink: DeepLink

    @Autowired
    lateinit var server: Server

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @Autowired
    lateinit var userService: UserService

    // BotをFollowした(アプリから or 直接)
    @EventMapping
    fun handleFollowEvent(event: FollowEvent): TemplateMessage {
        println("event: $event")
        val userId = event.source.userId
        val reseponse = restOperations.postForObject("/bot/user/$userId/linkToken", "", LinkToken::class.java)
        if (reseponse?.linkToken == null) {
            val imageUri = "https://5ddb0f2d.ngrok.io/static/image/thank_you.png"
            return TemplateMessage.builder()
                    .altText("連携に失敗しました。大変恐れ入りますが、このアカウントを一度ブロックし、再度友達に追加してください。")
                    .template(ButtonsTemplate.builder()
                            .thumbnailImageUrl(imageUri)
                            .imageSize("contain")
                            .title("Thank you!!")
                            .text("連携に失敗しました。大変恐れ入りますが、このアカウントを一度ブロックし、再度友達に追加してください。")
                            .build())
                    .build()
        }
        val imageUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(server.hostName)
                .path("/static/image/thank_you.png")
                .build()
        val actionUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.domain)
                .path("link_start")
                //.queryParam("token", reseponse.linkToken)
                .build()
        return TemplateMessage.builder()
                .altText("友たち追加ありがとうございます。以下のボタンをタップして、アプリと連携しましょう!。")
                .template(ButtonsTemplate.builder()
                        .thumbnailImageUrl(imageUri.toString())
                        .imageSize("contain")
                        .title("Thank you!!")
                        .text("友たち追加ありがとうございます。以下のボタンをタップして、アプリと連携しましょう!")
                        .actions(listOf(URIAction("連携する", actionUri.toString())))
                        .build()
                )
                .build()
    }

    @EventMapping
    fun handleAccountLinkEvent(event: AccountLinkEvent): TemplateMessage {
        println("event: $event")
        return if (event.link.result == LinkContent.Result.OK) {
            val appUser = appUserRepository.findByNonce(event.link.nonce)
            if (appUser != null) {
                appUser.linkDateTime = Date()
                appUser.lineId = event.source.userId
                appUserRepository.save(appUser)
                val actionUri = UriComponentsBuilder.newInstance()
                        .scheme("https")
                        .host(deeplink.domain)
                        .path("link_complete")
                        .build()
                TemplateMessage.builder()
                        .altText("アプリに戻って自分好みのボットを作りましょう！")
                        .template(ButtonsTemplate.builder()
                                .title("連携が完了しました")
                                .text("アプリに戻って自分好みのボットを作りましょう！")
                                .actions(listOf(URIAction("アプリへ", actionUri.toString())))
                                .build()
                        )
                        .build()
            } else {
                TemplateMessage.builder()
                        .altText("失敗しました")
                        .template(ButtonsTemplate.builder()
                                .title("失敗しました")
                                .text("失敗しました")
                                .build()
                        )
                        .build()
            }
        } else {
            val actionUri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host(deeplink.domain)
                    .build()
            TemplateMessage.builder()
                    .altText("連携に失敗しました。")
                    .template(ButtonsTemplate.builder()
                            .title("連携に失敗しました。")
                            .text("もう一度最初からやり直してください")
                            .actions(listOf(URIAction("アプリへ", actionUri.toString())))
                            .build()
                    )
                    .build()
        }
    }

    @EventMapping
    fun handleJoinEvent(event: JoinEvent): TemplateMessage {
        println("event: $event")
        val groupId  = event.source.senderId

        val imageUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(server.hostName)
                .path("/static/image/thank_you.png")
                .build()
        val actionUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.domain)
                .path("link_start")
                .queryParam("group_id", groupId)
                .build()
        return TemplateMessage.builder()
                .altText("アプリでBotの設定をしましょう!")
                .template(ButtonsTemplate.builder()
                        .title("グループへ招待ありがとうございます!")
                        .text("アプリでBotの設定をしましょう!")
                        .actions(listOf(URIAction("アプリへ", actionUri.toUriString())))
                        .build()
                )
                .build()
    }

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>): TextMessage {
        println("event: $event")
        val lineId = event.source.userId
        val groupId  = event.source.senderId

        return TextMessage(event.message.text)
    }

    @EventMapping
    fun handleDefaultMessageEvent(event: Event) {
        println("event: defaultEvent")
    }

}
