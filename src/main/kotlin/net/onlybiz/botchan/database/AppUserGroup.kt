package net.onlybiz.botchan.database

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "app_user_groups")
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
        @MapsId("appUserId")
        var appUser: AppUser? = null,

        @ManyToOne
        @MapsId("groupId")
        var group: Group? = null

) : CommonEntity()

@Embeddable
class AppUserGroupId(
        var appUserId: String? = null,
        var groupId: String? = null
): Serializable
