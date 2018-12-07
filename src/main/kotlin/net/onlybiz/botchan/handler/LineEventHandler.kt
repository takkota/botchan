package net.onlybiz.botchan.api

import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.FollowEvent
import com.linecorp.bot.model.event.JoinEvent
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.event.MessageEvent
import net.onlybiz.botchan.model.LinkToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@LineMessageHandler
class LineEventHandler {

    @Autowired
    lateinit var restOperations: RestOperations

    // BotをFollowした(アプリから or 直接)
    @EventMapping
    fun handleFollowEvent(event: FollowEvent): TextMessage {
        println("event: $event")
        val userId = event.source.userId
        val reseponse = restOperations.getForObject("/bot/user/$userId/linkToken", LinkToken::class.java)

        return TextMessage("test")
    }

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>): TextMessage {
        println("event: $event")
        return TextMessage(event.message.text)
    }

    @EventMapping
    fun handleDefaultMessageEvent(event: Event) {
        println("event: $event")
    }

}
