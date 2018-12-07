package net.onlybiz.botchan

import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.SpringApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@SpringBootApplication
class BotchanApplication

fun main(args: Array<String>) {
    runApplication<BotchanApplication>(*args)
}
