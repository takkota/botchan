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

    @Transactional
    fun saveBotDetail(id: Long? = null, userId: String, groupIds: List<String> = listOf(), replyCondition: BotReplyCondition? = null, pushCondition: BotPushCondition? = null, message: Message) {
        try {
            val appUser = appUserRepository.findById(userId).get()
            val groups = roomRepository.findAllById(groupIds)
            // 新規登録
            val botDetail = BotDetail(appUser = appUser, rooms = groups, message = message)
            if (id != null) {
                // 更新
                botDetail.id = id
            }
            val savedBotDetail = botDetailRepository.saveAndFlush(botDetail)

            // 関連テーブルのボット条件を保存
            if (replyCondition != null) {
                replyCondition.botDetail = savedBotDetail
                botReplyConditionRepository.save(replyCondition)
            }

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