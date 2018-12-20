package net.onlybiz.botchan.database

import javax.persistence.*


@Entity
@Table(name = "room")
data class Room(
        @Id
        @Column(length = 36)
        var id: String? = null,

        @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true) // 中間テーブルとのリレーション
        // mappedByを使うことでテーブルの作成を抑制する。(こちらはオーナーではないことを示す)
        var appUserRooms: List<AppUserRoom>? = null,

        @ManyToMany(mappedBy = "rooms", cascade = [CascadeType.PERSIST]) // CascadeType.Allだとdelete時にbotDetailまで消されるので
        var botDetails: List<BotDetail>? = null
) : CommonEntity() {
}
