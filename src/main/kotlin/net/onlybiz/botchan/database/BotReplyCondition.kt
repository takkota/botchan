package net.onlybiz.botchan.database

import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "bot_reply_condition")
data class BotReplyCondition(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @Column
        var keyword: String? = null,

        @Column
        var matchMethod: String? = null,

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "bot_detail_id")
        var botDetail: BotDetail? = null
) : CommonEntity()
