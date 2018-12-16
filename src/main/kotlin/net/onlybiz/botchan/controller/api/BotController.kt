package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.BotDetail
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.service.BotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/bot")
class BotController {

    @Autowired
    lateinit var botService: BotService

    // ボット一覧を取得
    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    fun getBotList(@RequestBody basicParam: BasicParameter): List<BotDetail>? {
        return botService.findBotList(basicParam.userId)
    }
}
