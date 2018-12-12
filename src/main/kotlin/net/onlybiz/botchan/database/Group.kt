package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
data class Group(
        @Id
        @Column(length = 36)
        var id: String? = null,

        @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true) // 中間テーブルとのリレーション
        // mappedByを使うことでテーブルの作成を抑制する。(こちらはオーナーではないことを示す)
        var appUserGroups: List<AppUserGroup>? = null,

        @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true) // 中間テーブルとのリレーション
        var botReplies: List<BotReply>? = null
) : CommonEntity()
