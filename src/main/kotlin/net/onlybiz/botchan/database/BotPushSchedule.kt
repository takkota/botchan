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

        @OneToOne(fetch = FetchType.LAZY, mappedBy = "botReplyCondition")
        var botDetail: BotDetail? = null

) : CommonEntity()
