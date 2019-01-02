package net.onlybiz.botchan.model.api.response

import com.linecorp.bot.model.message.Message
import java.util.*

data class BotDetailResponse(
        val botId: Long,
        val title: String,
        val botType: String,
        val groupIds: List<String>,
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
    val scheduleTime: Date
)
