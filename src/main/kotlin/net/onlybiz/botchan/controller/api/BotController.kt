package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.BotDetail
import net.onlybiz.botchan.database.BotPushSchedule
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.model.api.parameter.BotDetailParameter
import net.onlybiz.botchan.model.api.parameter.BotPushParameter
import net.onlybiz.botchan.model.api.parameter.BotReplyParameter
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
    fun getBotList(@RequestBody basicParam: BasicParameter): BotListResponse? {
        val bots = botService.findBotList(basicParam.userId)
        print("testd" + bots.toString())
        return BotListResponse(botService.findBotList(basicParam.userId) ?: listOf())
    }

    // ボット詳細を取得
    @RequestMapping(value = ["/detail"], method = [RequestMethod.POST])
    fun getBotDetail(@RequestBody body: BotDetailParameter): BotDetail? {
        body.botId ?: return null
        return botService.findBotDetail(body.botId!!)
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
