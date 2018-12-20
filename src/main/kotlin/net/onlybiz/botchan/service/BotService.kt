package net.onlybiz.botchan.service

import com.linecorp.bot.model.message.Message
import net.onlybiz.botchan.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BotService {

    @Autowired
    private lateinit var appUserRepository: AppUserRepository
    @Autowired
    private lateinit var roomRepository: RoomRepository
    @Autowired
    private lateinit var botDetailRepository: BotDetailRepository
    @Autowired
    private lateinit var botReplyConditionRepository: BotReplyConditionRepository
    @Autowired
    private lateinit var botPushScheduleRepository: BotPushScheduleRepository

    @Transactional
    fun saveBotDetail(id: Long? = null, userId: String, roomIds: List<String> = listOf(), replyConditionParam: BotReplyCondition? = null, pushScheduleParam: BotPushSchedule? = null, message: Message) {
        try {
            val appUser = appUserRepository.findById(userId).get()
            val rooms = roomRepository.findAllById(roomIds)
            val botDetail = if (id == null) {
                // 新規登録
                BotDetail(appUser = appUser, rooms = rooms, message = message).apply {
                    if (replyConditionParam != null) {
                        this.botReplyCondition = replyConditionParam
                    }
                    if (pushScheduleParam != null) {
                        botPushSchedule = pushScheduleParam
                    }
                }
            } else {
                // 更新
                botDetailRepository.findById(id).get().apply {
                    this.rooms = rooms
                    if (replyConditionParam != null) {
                        this.botReplyCondition?.apply {
                            keyword = replyConditionParam.keyword
                            matchMethod = replyConditionParam.matchMethod
                            reactToOwnerOnly = replyConditionParam.reactToOwnerOnly
                        }
                    }
                    if (pushScheduleParam != null) {
                        botPushSchedule?.apply {
                            this.scheduleTime = pushScheduleParam.scheduleTime
                        }
                    }
                }
            }
            botDetailRepository.save(botDetail)

            // 関連テーブルを保存
            //if (replyCondition != null) {
            //    replyCondition.botDetail = savedBotDetail
            //    botReplyConditionRepository.save(replyCondition)
            //}
            //if (pushSchedule != null) {
            //    pushSchedule.botDetail = savedBotDetail
            //    botPushScheduleRepository.save(pushSchedule)
            //}

        } catch (e: NoSuchElementException) {
            return
        }
    }

    @Transactional(readOnly = true)
    fun findBotDetail(id: Long): BotDetail? {
        return try {
            val botDetail = botDetailRepository.findById(id)
            botDetail.get()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    @Transactional(readOnly = true)
    fun findBotList(userId: String): List<BotDetail>? {
        return try {
            botDetailRepository.findByAppUserId(userId)
        } catch (e: NoSuchElementException) {
            null
        }
    }
}