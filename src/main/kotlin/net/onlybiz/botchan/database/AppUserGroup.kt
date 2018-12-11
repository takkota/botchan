package net.onlybiz.botchan.database

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(
        name = "app_user_groups",
        uniqueConstraints = [
                UniqueConstraint(columnNames = ["app_user_id", "group_id"])
        ]
)
data class AppUserGroup(
        @Id
        @Column
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: String? = null,

        @ManyToOne
        @Column(name="app_user_id")
        var appUser: AppUser? = null,

        @ManyToOne
        @Column(name="group_id")
        var group: Group? = null,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity()
