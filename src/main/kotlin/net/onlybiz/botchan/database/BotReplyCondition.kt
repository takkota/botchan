package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "bot_reply_conditions")
data class BotReplyCondition(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        var keyword: String? = null,

        var matchMethod: String? = null,

        var validTimeFrom: Date? = null,

        var validTimeTo: Date? = null,

        var reactToOwnerOnly: Boolean = false,

        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var botReply: BotReply? = null
) : CommonEntity()
