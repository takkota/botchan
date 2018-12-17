package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotPushCondition
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter

class BotDetailSaveParameter: BasicParameter() {
    var id: Long? = null
    lateinit var groupIds: List<String>
    var replyCondition: BotReplyCondition? = null
    var pushCondition: BotPushCondition? = null
    lateinit var message: Message
}
