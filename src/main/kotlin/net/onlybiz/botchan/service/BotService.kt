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
    private lateinit var groupRepository: GroupRepository

    @Autowired
    private lateinit var botReplyRepository: BotReplyRepository

    @Transactional
    fun saveBotDetail(id: Long? = null, userId: String, groupIds: List<String> = listOf(), replyCondition: BotReplyCondition? = null, pushCondition: BotPushCondition? = null, message: Message) {
        try {
            val appUser = appUserRepository.findById(userId).get()
            val groups = groupRepository.findAllById(groupIds)
            // 新規登録
            val botDetail = BotDetail(appUser = appUser, groups = groups, botReplyCondition = replyCondition, botPushCondition = pushCondition, message = message)
            if (id != null) {
                // 更新
                botDetail.id = id
            }
            botReplyRepository.save(botDetail)
        } catch (e: NoSuchElementException) {
            return
        }
    }

    @Transactional(readOnly = true)
    fun findBotDetail(id: Long): BotDetail? {
        return try {
            val botDetail = botReplyRepository.findById(id)
            botDetail.get()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    @Transactional(readOnly = true)
    fun findBotList(userId: String): List<BotDetail>? {
        return try {
            botReplyRepository.findByAppUserId(userId)
        } catch (e: NoSuchElementException) {
            null
        }
    }
}