package net.onlybiz.botchan.database

import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "bot_reply_condition")
data class BotReplyCondition(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        var keyword: String? = null,

        var matchMethod: String? = null,

        var reactToOwnerOnly: Boolean = false,

        @OneToOne(mappedBy = "botReplyCondition", optional = false)
        var botDetail: BotDetail? = null
) : CommonEntity()
