package net.onlybiz.botchan.service

import net.onlybiz.botchan.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BotService {

    @Autowired
    private lateinit var botReplyRepository: BotReplyRepository

    @Transactional(readOnly = true)
    fun test() {
        val botReplies = botReplyRepository.findAll()
        if (botReplies.size > 0) {
        }
    }

    @Transactional
    fun saveBotReply() {
        val botReplies = botReplyRepository.findAll()
        if (botReplies.size > 0) {
        }
    }
}