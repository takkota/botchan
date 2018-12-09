package net.onlybiz.botchan.api

import com.linecorp.bot.model.Multicast
import com.linecorp.bot.model.action.URIAction
import com.linecorp.bot.model.event.*
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.event.link.LinkContent
import com.linecorp.bot.model.event.source.GroupSource
import com.linecorp.bot.model.event.source.RoomSource
import com.linecorp.bot.model.message.TemplateMessage
import com.linecorp.bot.model.message.template.ButtonsTemplate
import net.onlybiz.botchan.database.AppUserGroup
import net.onlybiz.botchan.database.Group
import net.onlybiz.botchan.database.AppUserRepository
import net.onlybiz.botchan.model.line.response.GroupMember
import net.onlybiz.botchan.model.line.response.LinkToken
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
    lateinit var server: Server

    @Autowired
    lateinit var appUserRepository: AppUserRepository

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
                .host(server.hostName)
                .path("/account/link")
                .queryParam("linkToken", reseponse.linkToken)
                .build()
        println("testd:actionUri:${actionUri.toString()}")
        return TemplateMessage.builder()
                .altText("友たち追加ありがとうございます。以下のボタンをタップして、アプリとの連携を完了させてください。")
                .template(ButtonsTemplate.builder()
                        .thumbnailImageUrl(imageUri.toString())
                        .imageSize("contain")
                        .title("Thank you!!")
                        .text("友たち追加ありがとうございます。以下のボタンをタップして、アプリとの連携を完了させてください。")
                        .actions(listOf(URIAction("連携する", actionUri.toString())))
                        .build()
                )
                .build()
    }

    @EventMapping
    fun handleAccountLinkEvent(event: AccountLinkEvent): TemplateMessage {
        return if (event.link.result == LinkContent.Result.OK) {
            val appUser = appUserRepository.findByNonce(event.link.nonce)
            appUser.linkDateTime = Date()
            appUser.lineId = event.source.userId
            appUserRepository.save(appUser)
            val actionUri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host(server.hostName)
                    .path("/account/link")
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
            val actionUri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host(server.hostName)
                    .path("/account/link")
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
    fun handleJoinEvent(event: JoinEvent) {
        println("join event user_id: " + event.source.userId)
        var groupId: String? = null
        when (event.source) {
            is RoomSource -> {
                groupId = (event.source as RoomSource).roomId
            }
            is GroupSource -> {
                groupId = (event.source as GroupSource).groupId
            }
            else -> {
                println("no id")
            }
        }
        if (groupId != null) {
            val reseponse = restOperations.getForObject("/bot/group/$groupId/members/ids", GroupMember::class.java)
            if (reseponse != null) {
                // joinしたらメンバーを全員スキャン & ボットを持っている人であれば、group_idを紐付け
                var appUsers = appUserRepository.findAllById(reseponse.memberIds)
                appUsers = appUsers.map { appUser ->
                    //appUser.appUserGroups?.plus(AppUserGroup(appUser, Group(groupId), "グループ").apply {
                    //})
                    appUser
                }
                appUserRepository.saveAll(appUsers)

                // それとは別にLinePushでグループにボットが入ったことを教えてあげる。(アプリへのリンク付きで)
                // (アプリ側でグループ名を設定させる。)
                val message = TemplateMessage.builder()
                        .altText("参加しているグループにボットが入室しました。アプリでグループに名前をつけてください。(ボットを招待した記憶がない場合、グループ内の他の誰かがボットを招待した可能性もあります。)")
                        .template(ButtonsTemplate.builder()
                                .title("Thank you!!")
                                .text("参加しているグループにボットが入室しました。アプリでグループに名前をつけてください。(ボットを招待した記憶がない場合、グループ内の他の誰かがボットを招待した可能性もあります。")
                                .build())
                        .build()
                val lineIds = appUsers.map { it.lineId }.toSet()
                restOperations.postForObject("bot/message/multicast", Multicast(lineIds, message), Void::class.java)
            }
        }
    }

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>): TextMessage {
        println("event: messageEvent")
        return TextMessage(event.message.text)
    }

    @EventMapping
    fun handleDefaultMessageEvent(event: Event) {
        println("event: defaultEvent")
    }

}
