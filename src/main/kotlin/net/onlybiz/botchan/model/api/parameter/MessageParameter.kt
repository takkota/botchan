package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.model.api.parameter.BasicParameter

class MessageParameter: BasicParameter() {
    lateinit var groupIds: List<String>
    lateinit var message: Message
}
