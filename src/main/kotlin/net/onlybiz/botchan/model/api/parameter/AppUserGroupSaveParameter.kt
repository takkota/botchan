package net.onlybiz.botchan.model.api.parameter

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter

class AppUserGroupSaveParameter: BasicParameter() {
    var id: Long? = null
    lateinit var groupId: String
    var displayName: String? = null
}
