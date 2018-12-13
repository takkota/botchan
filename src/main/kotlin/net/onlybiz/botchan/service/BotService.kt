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
    fun saveBotDetail(id: Long? = null, userId: String, groupIds: List<String> = listOf(), conditions: List<BotReplyCondition> = listOf(), message: Message) {
        try {
            val appUser = appUserRepository.findById(userId).get()
            val groups = groupRepository.findAllById(groupIds)
            val botDetail = if (id == null) {
                // 新規登録
                BotDetail(appUser = appUser, groups = groups, botReplyConditions = conditions, message = message)
            } else {
                // 更新
                BotDetail(id = id, appUser = appUser, groups = groups, botReplyConditions = conditions, message = message)
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