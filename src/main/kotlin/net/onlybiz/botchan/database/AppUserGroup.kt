package net.onlybiz.botchan.database

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "groups")
class AppUserGroup : CommonEntity(), Serializable {
    @EmbeddedId
    var appUserGroupId: AppUserGroupId? = null

    @Column(name="display_name")
    var displayName: String? = null
}

@Embeddable
class AppUserGroupId(
        @ManyToOne
        @JoinColumn(name = "app_user_id", nullable = false)
        var appUser: AppUser? = null,

        @ManyToOne
        @JoinColumn(name = "group_id", nullable = false)
        var group: Group? = null
): Serializable
