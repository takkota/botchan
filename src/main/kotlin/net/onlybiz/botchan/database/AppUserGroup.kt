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
        @Column(name="id")
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        var appUser: AppUser? = null,

        @ManyToOne
        var group: Group? = null,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity()
