package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.BotDetail
import net.onlybiz.botchan.model.api.parameter.BotDetailGetParameter
import net.onlybiz.botchan.model.api.parameter.BotDetailSaveParameter
import net.onlybiz.botchan.service.BotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/bot/detail")
class BotDetailController {

    @Autowired
    lateinit var botService: BotService

    // ボット詳細を保存
    @RequestMapping(value = ["/save"], method = [RequestMethod.POST])
    fun saveBotDetail(@RequestBody body: BotDetailSaveParameter) {
        botService.saveBotDetail(id = body.id, userId = body.userId, groupIds = body.groupIds, conditions = body.condition, message = body.message)
    }

    // ボット詳細を取得
    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    fun getBotDetail(@RequestBody body: BotDetailGetParameter): BotDetail? {
        body.id ?: return null
        return botService.findBotDetail(body.id!!)
    }
}
