package net.onlybiz.botchan.domain

import net.onlybiz.botchan.repository.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: JpaRepository<Test, Long>