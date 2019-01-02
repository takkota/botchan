package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.BotDetail
import net.onlybiz.botchan.database.BotPushSchedule
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.model.api.parameter.BotDetailParameter
import net.onlybiz.botchan.model.api.parameter.BotPushParameter
import net.onlybiz.botchan.model.api.parameter.BotReplyParameter
import net.onlybiz.botchan.model.api.response.BotDetailResponse
import net.onlybiz.botchan.model.api.response.BotListResponse
import net.onlybiz.botchan.service.BotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/bot")
class BotController {

    @Autowired
    lateinit var botService: BotService

    // ボット一覧を取得
    @RequestMapping(method = [RequestMethod.POST])
    fun getBotList(@RequestBody basicParam: BasicParameter): BotListResponse {
        val botDetails = botService.findBotList(basicParam.userId).map { bot ->
            BotDetailResponse(
                    botId = bot.id!!,
                    botType = if (bot.botReplyCondition != null) "reply" else "push",
                    groupIds = bot.lineGroups?.map { it.id!! } ?: listOf(),
                    message = bot.message,
                    title = bot.title!!
            )
        }

        return BotListResponse(botDetails)
    }

    // ボット詳細を取得
    @RequestMapping(value = ["/detail"], method = [RequestMethod.POST])
    fun getBotDetail(@RequestBody body: BotDetailParameter): BotDetailResponse? {
        body.botId ?: return null
        return botService.findBotDetail(body.botId!!)?.let { bot ->
            BotDetailResponse(
                    botId = bot.id!!,
                    message = bot.message,
                    groupIds = bot.lineGroups?.map { it.id!! } ?: listOf(),
                    botType = if (bot.botReplyCondition != null) "reply" else "push",
                    title = bot.title!!
            )
        }
    }

    @RequestMapping(value = ["/delete"], method = [RequestMethod.POST])
    fun deleteBot(@RequestBody body: BotDetailParameter) {
        body.botId ?: return
        return botService.deleteBot(body.botId!!)
    }

    // ボット詳細(type=応答)を保存
    @RequestMapping(value = ["/reply/save"], method = [RequestMethod.POST])
    fun saveBotReply(@RequestBody body: BotReplyParameter) {
        val condition = BotReplyCondition(
                keyword =  body.keyword,
                matchMethod =  body.matchMethod
        )
        botService.saveBotDetail(id = body.botId, userId = body.userId, groupIds = body.lineGroupIds, replyConditionParam = condition, message = body.message)
    }

    // ボット詳細を保存
    @RequestMapping(value = ["/push/save"], method = [RequestMethod.POST])
    fun saveBotPush(@RequestBody body: BotPushParameter) {
        val condition = BotPushSchedule(
                scheduleTime = body.scheduleTime
        )
        botService.saveBotDetail(id = body.botId, userId = body.userId, groupIds = body.lineGroupIds, pushScheduleParam = condition, message = body.message)
    }

}
