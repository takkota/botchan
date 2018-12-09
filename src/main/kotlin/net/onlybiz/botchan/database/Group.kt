package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
data class Group(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: String? = null
) : CommonEntity() {

        @OneToMany // 中間テーブルとのリレーション
        lateinit var appUserGroups: Set<AppUserGroup>
}
