package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.BotPushSchedule
import net.onlybiz.botchan.database.BotReplyCondition
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.model.api.parameter.BotDetailParameter
import net.onlybiz.botchan.model.api.parameter.BotPushParameter
import net.onlybiz.botchan.model.api.parameter.BotReplyParameter
import net.onlybiz.botchan.model.api.response.*
import net.onlybiz.botchan.service.BotService
import net.onlybiz.botchan.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/bot")
class BotController {

    @Autowired
    lateinit var botService: BotService

    @Autowired
    lateinit var appUserService: UserService

    // ボット一覧を取得
    @RequestMapping(method = [RequestMethod.POST])
    fun getBotList(@RequestBody basicParam: BasicParameter): BotListResponse {
        val botDetails = botService.findBotList(basicParam.userId)

        val botList = botDetails.map { bot ->
            // 接続されたグループを抽出する。
            val attachedLineGroupIds = bot.lineGroups?.map { it.id } ?: listOf()
            val attachedLineGroups = bot.appUser?.appUserLineGroups?.filter {
                it.lineGroup?.id in attachedLineGroupIds
            } ?: listOf()

            BotDetailResponse(
                    botId = bot.id!!,
                    message = bot.message,
                    lineGroups = attachedLineGroups.map {
                        AppUserLineGroupResponse(
                                id = it.id,
                                lineGroupId = it.lineGroup?.id,
                                displayName = it.displayName
                        )
                    },
                    botType = if (bot.botReplyCondition?.isEmpty() == true ) "push" else "reply",
                    title = bot.title!!,
                    replyCondition = if (bot.botReplyCondition?.isNotEmpty() == true) {
                        BotReplyConditionResponse(
                                id = bot.botReplyCondition!!.first().id!!,
                                keyword = bot.botReplyCondition!!.first().keyword!!,
                                matchMethod = bot.botReplyCondition!!.first().matchMethod!!
                        )
                    } else null,
                    pushSchedule = if (bot.botPushSchedule?.isNotEmpty() == true) {
                        BotPushScheduleResponse(
                                id = bot.botPushSchedule!!.first().id!!,
                                days = bot.botPushSchedule!!.first().days!!,
                                scheduleTime = bot.botPushSchedule!!.first().scheduleTime!!
                        )
                    } else null
            )
        }

        return BotListResponse(botList)
    }

    // ボット詳細を取得
    @RequestMapping(value = ["/detail"], method = [RequestMethod.POST])
    fun getBotDetail(@RequestBody body: BotDetailParameter): BotDetailResponse? {
        body.botId ?: return null
        return botService.findBotDetail(body.botId!!)?.let { bot ->
            // 接続されたグループを抽出する。
            val attachedLineGroupIds = bot.lineGroups?.map { it.id } ?: listOf()
            val attachedLineGroups = bot.appUser?.appUserLineGroups?.filter {
                it.lineGroup?.id in attachedLineGroupIds
            } ?: listOf()

            val botDetail = BotDetailResponse(
                    botId = bot.id!!,
                    message = bot.message,
                    botType = if (bot.botReplyCondition?.isEmpty() == true ) "push" else "reply",
                    lineGroups = attachedLineGroups.map {
                        AppUserLineGroupResponse(
                                id = it.id,
                                lineGroupId = it.lineGroup?.id,
                                displayName = it.displayName
                        )
                    },
                    title = bot.title!!,
                    replyCondition = if (bot.botReplyCondition?.isNotEmpty() == true) {
                        BotReplyConditionResponse(
                                id = bot.botReplyCondition!!.first().id!!,
                                keyword = bot.botReplyCondition!!.first().keyword!!,
                                matchMethod = bot.botReplyCondition!!.first().matchMethod!!
                        )
                    } else null,
                    pushSchedule = if (bot.botPushSchedule?.isNotEmpty() == true) {
                        BotPushScheduleResponse(
                                id = bot.botPushSchedule!!.first().id!!,
                                days = bot.botPushSchedule!!.first().days!!,
                                scheduleTime = bot.botPushSchedule!!.first().scheduleTime!!
                        )
                    } else null
            )
            botDetail
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
                id = body.replyConditionId,
                keyword =  body.keyword,
                matchMethod =  body.matchMethod
        )
        botService.saveBotDetail(id = body.botId, title = body.title, userId = body.userId, groupIds = body.lineGroupIds, replyConditionParam = condition, message = body.message)
    }

    // ボット詳細を保存
    @RequestMapping(value = ["/push/save"], method = [RequestMethod.POST])
    fun saveBotPush(@RequestBody body: BotPushParameter) {
        val condition = BotPushSchedule(
                id = body.pushScheduleId,
                days = body.days,
                scheduleTime = body.scheduleTime
        )
        botService.saveBotDetail(id = body.botId, title = body.title, userId = body.userId, groupIds = body.lineGroupIds, pushScheduleParam = condition, message = body.message)
    }

}
