package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "bot_push_condition")
data class BotPushCondition(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        var scheduleTime: Date? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var botDetail: BotDetail? = null
) : CommonEntity()
