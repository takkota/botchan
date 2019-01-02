package net.onlybiz.botchan.model.api.response

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.BotPushSchedule
import net.onlybiz.botchan.database.BotReplyCondition

class BotListResponse(val botList: List<BotDetailResponse>)
