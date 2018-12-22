package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter

class AppUserSaveParameter: BasicParameter() {
    lateinit var roomId: String
    var displayName: String? = null
}
