package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message

class BotReplyParameter: BasicParameter() {
    var replyConditionId: Long? = null
    var botId: Long? = null
    var lineGroupIds: List<String> = listOf()
    lateinit var keyword: String
    lateinit var matchMethod: String
    lateinit var message: Message
}
