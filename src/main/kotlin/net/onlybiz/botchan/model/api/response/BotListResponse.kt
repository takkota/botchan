package net.onlybiz.botchan.model.api.response

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotDetail

class BotListResponse(val botList: List<BotDetailResponse>)

data class BotDetailResponse(
        val botId: Long,
        val title: String,
        val botType: String,
        val groupIds: List<String>,
        val message: Message?
)
