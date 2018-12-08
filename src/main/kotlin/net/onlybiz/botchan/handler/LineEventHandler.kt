package net.onlybiz.botchan.api

import com.linecorp.bot.model.action.URIAction
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.FollowEvent
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.message.TemplateMessage
import com.linecorp.bot.model.message.template.ButtonsTemplate
import net.onlybiz.botchan.model.line.LinkToken
import net.onlybiz.botchan.settings.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.client.RestOperations
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.UriComponentsBuilder
import java.net.InetAddress

@LineMessageHandler
class LineEventHandler {

    @Autowired
    lateinit var restOperations: RestOperations

    @Autowired
    lateinit var server: Server

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
        println("testd:imageUri:${imageUri.toString()}")
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
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>): TextMessage {
        println("event: messageEvent")
        return TextMessage(event.message.text)
    }

    @EventMapping
    fun handleDefaultMessageEvent(event: Event) {
        println("event: defaultEvent")
    }

}
