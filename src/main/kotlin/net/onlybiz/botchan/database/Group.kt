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

        @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true) // 中間テーブルとのリレーション
        var appUserGroups: Set<AppUserGroup>? = null
}
