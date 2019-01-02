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
    private lateinit var lineGroupRepository: LineGroupRepository
    @Autowired
    private lateinit var botDetailRepository: BotDetailRepository
    @Autowired
    private lateinit var botReplyConditionRepository: BotReplyConditionRepository
    @Autowired
    private lateinit var botPushScheduleRepository: BotPushScheduleRepository

    @Transactional
    fun saveBotDetail(id: Long? = null, title: String, userId: String, groupIds: List<String> = listOf(), replyConditionParam: BotReplyCondition? = null, pushScheduleParam: BotPushSchedule? = null, message: Message): BotDetail? {
        try {
            val appUser = appUserRepository.findById(userId).get()
            val groups = lineGroupRepository.findAllById(groupIds)
            val botDetail = if (id == null) {
                // 新規登録
                BotDetail().apply {
                    this.appUser = appUser
                    this.title = title
                    this.message = message
                    this.lineGroups = groups
                    if (replyConditionParam != null) {
                        botReplyCondition?.add(replyConditionParam)
                    }
                    if (pushScheduleParam != null) {
                        botPushSchedule?.add(pushScheduleParam)
                    }
                }
            } else {
                // 更新
                botDetailRepository.findById(id).get().apply {
                    this.appUser = appUser
                    this.title = title
                    this.message = message
                    this.lineGroups = groups
                    if (replyConditionParam != null) {
                        botReplyCondition?.add(replyConditionParam)
                        // スケジュールは削除
                        botPushSchedule = null
                    }
                    if (pushScheduleParam != null) {
                        botPushSchedule?.add(pushScheduleParam)
                        // 返答条件は削除
                        botReplyCondition = null
                    }
                }
            }
            replyConditionParam?.botDetail = botDetail
            pushScheduleParam?.botDetail = botDetail
            return botDetailRepository.save(botDetail)

        } catch (e: NoSuchElementException) {
            return null
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
    fun findBotList(userId: String): List<BotDetail> {
        return try {
            val user = appUserRepository.findById(userId)
            return user.get().botDetails ?: listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

    @Transactional
    fun deleteBot(botId: Long) {
        return try {
            botDetailRepository.deleteById(botId)
        } catch (e: NoSuchElementException) {
            print(e)
        }
    }
}