package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import java.util.*

class BotPushParameter: BasicParameter() {
    var id: Long? = null
    var botId: Long? = null
    var lineGroupIds: List<String> = listOf()
    lateinit var scheduleTime: Date
    lateinit var message: Message
}
