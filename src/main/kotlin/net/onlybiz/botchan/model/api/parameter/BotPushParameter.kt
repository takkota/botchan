package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import java.util.*

class BotPushParameter: BasicParameter() {
    var botId: Long? = null
    var roomIds: List<String> = listOf()
    lateinit var scheduleTime: Date
    lateinit var message: Message
}
