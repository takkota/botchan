package net.onlybiz.botchan.database

import javax.persistence.*


@Entity
@Table(name = "lineGroup")
data class LineGroup(
        @Id
        @Column(length = 36)
        var id: String? = null,

        @OneToMany(mappedBy = "lineGroup", orphanRemoval = true) // 中間テーブルとのリレーション
        // mappedByを使うことでテーブルの作成を抑制する。(こちらはオーナーではないことを示す)
        var appUserLineGroups: List<AppUserLineGroup>? = null,

        @ManyToMany(mappedBy = "lineGroups") // CascadeType.Allだとdelete時にbotDetailまで消されるので
        var botDetails: List<BotDetail>? = null
) : CommonEntity() {
}
