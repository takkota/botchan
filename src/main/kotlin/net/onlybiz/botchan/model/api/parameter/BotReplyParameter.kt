package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message

class BotReplyParameter: BasicParameter() {
    var id: Long? = null
    var roomIds: List<String> = listOf()
    lateinit var keyword: String
    lateinit var matchMethod: String
    var reactToOwnerOnly: Boolean = false
    lateinit var message: Message
}
