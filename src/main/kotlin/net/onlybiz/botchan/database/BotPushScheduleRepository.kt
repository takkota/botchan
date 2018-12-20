package net.onlybiz.botchan.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BotPushScheduleRepository: JpaRepository<BotPushSchedule, Long> {
}
