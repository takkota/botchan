package net.onlybiz.botchan.model.api.response

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.LineGroup
import java.util.*

data class BotDetailResponse(
        val botId: Long,
        val title: String,
        val botType: String,
        val lineGroups: List<AppUserLineGroupResponse>,
        var replyCondition: BotReplyConditionResponse?,
        var pushSchedule: BotPushScheduleResponse?,
        val message: Message?
)

data class BotReplyConditionResponse(
    val id: Long? = null,
    val keyword: String,
    val matchMethod: String
)

data class BotPushScheduleResponse(
    val id: Long? = null,
    val days: Int = 0,
    val scheduleTime: Date
)
