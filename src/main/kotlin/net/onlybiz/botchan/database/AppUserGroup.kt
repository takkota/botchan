package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
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

) : CommonEntity()
