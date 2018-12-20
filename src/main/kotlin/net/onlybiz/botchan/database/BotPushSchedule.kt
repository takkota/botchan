package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "bot_push_condition")
data class BotPushSchedule(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,
        var scheduleTime: Date? = null,
        @OneToOne(mappedBy = "botPushSchedule", optional = false)
        var botDetail: BotDetail? = null
) : CommonEntity()
