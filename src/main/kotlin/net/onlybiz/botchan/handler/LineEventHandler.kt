package net.onlybiz.botchan.api

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
import net.onlybiz.botchan.service.UserService
import net.onlybiz.botchan.settings.DeepLink
import net.onlybiz.botchan.settings.Liff
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
    lateinit var liff: Liff

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
        val lineId = event.source.userId
        val imageUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(server.hostName)
                .path("/static/image/thank_you.png")
                .build()
        val actionUrl = liff.linkAction + "?lineId=$lineId"
        return TemplateMessage.builder()
                .altText("友たち追加ありがとうございます。以下のボタンをタップして、アプリと連携しましょう!。")
                .template(ButtonsTemplate.builder()
                        .thumbnailImageUrl(imageUri.toString())
                        .imageSize("contain")
                        .title("Thank you!!")
                        .text("友たち追加ありがとうございます。\n以下のボタンをタップして、アプリと連携しましょう!")
                        .actions(listOf(URIAction("連携する", actionUrl)))
                        .build()
                )
                .build()
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
        val actionUrl = liff.addGroupAction + "?lineGroupId=$groupId"
        return TemplateMessage.builder()
                .altText("アプリでBotの設定をしましょう!")
                .template(ButtonsTemplate.builder()
                        .thumbnailImageUrl(imageUri.toString())
                        .imageSize("contain")
                        .title("グループへ招待ありがとうございます!")
                        .text("アプリでBotの設定をしましょう!")
                        .actions(listOf(URIAction("アプリにグループを追加", actionUrl)))
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
