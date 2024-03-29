package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import java.util.*

class BotPushParameter: BasicParameter() {
    var pushScheduleId: Long? = null
    lateinit var title: String
    var days: Int = 0
    var botId: Long? = null
    var lineGroupIds: List<String> = listOf()
    lateinit var scheduleTime: Date
    lateinit var message: Message
}
