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

        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "bot_detail_id", nullable = false)
        var botDetail: BotDetail? = null
) : CommonEntity()
