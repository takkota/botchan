package net.onlybiz.botchan.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BotReplyRepository: JpaRepository<BotDetail, Long> {
    fun findByAppUserId(userId: String): List<BotDetail>
}
