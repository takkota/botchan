package net.onlybiz.botchan.database

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
@IdClass(AppUserGroupKey::class)
data class AppUserGroup(
        @Id
        @ManyToOne
        @JoinColumn(name = "app_user_id", nullable = false)
        var appUser: AppUser? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "group_id", nullable = false)
        var group: Group? = null,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity(), Serializable

class AppUserGroupKey: Serializable {
        var appUser: AppUser? = null
        var group: Group? = null
}
