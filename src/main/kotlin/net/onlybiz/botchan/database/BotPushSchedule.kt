package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "bot_push_schedule")
data class BotPushSchedule(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @Column
        var scheduleTime: Date? = null,

        @Column
        var days: Int? = null,

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "bot_detail_id")
        var botDetail: BotDetail? = null

) : CommonEntity()
