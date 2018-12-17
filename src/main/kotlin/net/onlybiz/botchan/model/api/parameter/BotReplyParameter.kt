package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotPushCondition
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter

class BotReplyParameter: BasicParameter() {
    var id: Long? = null
    var groupIds: List<String> = listOf()
    lateinit var keyword: String
    lateinit var matchMethod: String
    var reactToOwnerOnly: Boolean = false
    lateinit var message: Message
}
