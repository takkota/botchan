package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotPushCondition
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import java.util.*

class BotPushParameter: BasicParameter() {
    var id: Long? = null
    var groupIds: List<String> = listOf()
    lateinit var scheduleTime: Date
    lateinit var message: Message
}
