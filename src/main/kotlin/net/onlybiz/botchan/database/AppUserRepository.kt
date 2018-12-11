package net.onlybiz.botchan.database

import net.onlybiz.botchan.database.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository: JpaRepository<AppUser, String> {
    fun findByNonce(nonce: String): AppUser
    fun findByLineId(lineId: String): AppUser
}