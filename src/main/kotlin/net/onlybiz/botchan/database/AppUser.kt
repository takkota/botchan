package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "app_users")
data class AppUser(
        @Id
        // dataクラスで定義する場合、デフォルト値を入れておかないとInstantiationException: No default constructor for entityが発生する
        var id: String = UUID.randomUUID().toString(),

        @Column(name = "line_id", unique = true)
        var lineId: String? = null,

        @Column(unique = true)
        var nonce: String? = null,

        @Column(name = "link_date_time")
        var linkDateTime: Date? = null,

        @OneToMany(mappedBy = "appUser", cascade = [CascadeType.ALL], orphanRemoval = true) // 中間テーブルとのリレーション
        var appUserGroups: Set<AppUserGroup>? = null
) : CommonEntity()

