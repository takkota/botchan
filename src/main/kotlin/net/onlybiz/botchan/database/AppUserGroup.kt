package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
data class AppUserGroup(
        @Id
        @ManyToOne
        @Column(name = "app_user_id", nullable = false)
        var appUserId: AppUser? = null,

        @Id
        @ManyToOne
        @Column(name = "group_id", nullable = false)
        var groupId: Group? = null,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity()
