package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "app_user")
data class AppUser(
        @Id
        @Column(length = 36)
        // dataクラスで定義する場合、デフォルト値を入れておかないとInstantiationException: No default constructor for entityが発生する
        var id: String = UUID.randomUUID().toString(),

        @Column(name = "line_id", unique = true)
        var lineId: String? = null,

        @Column(unique = true)
        var nonce: String? = null,

        @Column(name = "link_date_time")
        var linkDateTime: Date? = null,

        @OneToMany(mappedBy = "appUser", cascade = [CascadeType.ALL]) // 中間テーブルとのリレーション
        // mappedByを使うことでテーブルの作成を抑制する。(こちらはオーナーではないことを示す)
        var appUserRooms: List<AppUserRoom>? = null,

        @OneToMany(mappedBy = "appUser", cascade = [CascadeType.ALL], orphanRemoval = true)
        var botDetails: List<BotDetail>? = null
) : CommonEntity()

