package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "app_user")
data class AppUser(
        @Id
        @Column(length = 36, updatable = true)
        // dataクラスで定義する場合、デフォルト値を入れておかないとInstantiationException: No default constructor for entityが発生する
        // 実際はParameterで受け取るので、初期値は使われない
        var id: String = UUID.randomUUID().toString(),

        @Column(name = "line_id", unique = true)
        var lineId: String? = null,

        @Column(unique = true)
        var nonce: String? = null,

        @Column(name = "link_date_time")
        var linkDateTime: Date? = null,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser") // 中間テーブルとのリレーション
        // mappedByを使うことでテーブルの作成を抑制する。(こちらはオーナーではないことを示す)
        var appUserLineGroups: List<AppUserLineGroup>? = null,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser", orphanRemoval = true)
        var botDetails: List<BotDetail>? = null
): CommonEntity()

