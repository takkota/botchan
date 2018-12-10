package net.onlybiz.botchan.database

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
data class AppUserGroup(
        @AttributeOverrides(
                AttributeOverride(name = "appUserId",column = Column(name = "app_user_id")),
                AttributeOverride(name = "groupId",column = Column(name = "group_id"))
        )
        @EmbeddedId
        var appUserGroupId: AppUserGroupId? = null,

        @Column(name="display_name")
        var displayName: String? = null,

        @ManyToOne
        @JoinColumn(name = "appUserId")
        var appUser: AppUser? = null,

        @ManyToOne
        @JoinColumn(name = "groupId")
        var group: Group? = null

) : CommonEntity()

@Embeddable
class AppUserGroupId(
        @Column(name="app_user_id")
        var appUserId: String? = null,

        @Column(name="group_id")
        var groupId: String? = null
): Serializable
