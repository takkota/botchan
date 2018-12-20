package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.BotDetail
import net.onlybiz.botchan.database.BotPushSchedule
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BotDetailGetParameter
import net.onlybiz.botchan.model.api.parameter.BotPushParameter
import net.onlybiz.botchan.model.api.parameter.BotReplyParameter
import net.onlybiz.botchan.service.BotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/bot/detail")
class BotDetailController {

    @Autowired
    lateinit var botService: BotService

    // ボット詳細(type=応答)を保存
    @RequestMapping(value = ["/reply/save"], method = [RequestMethod.POST])
    fun saveBotReply(@RequestBody body: BotReplyParameter) {
        val condition = BotReplyCondition(
                id = body.id,
                keyword =  body.keyword,
                matchMethod =  body.matchMethod,
                reactToOwnerOnly = body.reactToOwnerOnly
        )
        botService.saveBotDetail(id = body.id, userId = body.userId, roomIds = body.roomIds, replyCondition = condition, message = body.message)
    }

    // ボット詳細を保存
    @RequestMapping(value = ["/push/save"], method = [RequestMethod.POST])
    fun saveBotPush(@RequestBody body: BotPushParameter) {
        val condition = BotPushSchedule(
                id = body.id,
                scheduleTime = body.scheduleTime
        )
        botService.saveBotDetail(id = body.id, userId = body.userId, roomIds = body.roomIds, pushSchedule = condition, message = body.message)
    }

    // ボット詳細を取得
    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    fun getBotDetail(@RequestBody body: BotDetailGetParameter): BotDetail? {
        body.id ?: return null
        return botService.findBotDetail(body.id!!)
    }
}
