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

    @MapsId("appUserId") //AppUserGroupId(PK)のappUserを切り出してfieldとしているよ
    @JoinColumn(name = "app_user_id", referencedColumnName = "id") // 外テーブルからJoinされる時のカラムはapp_user_idだよ
    @ManyToOne
    var appUser: AppUser? = null

    @MapsId("groupId") //AppUserGroupId(PK)のappUserを切り出してfieldとしているよ
    @JoinColumn(name = "group_id", referencedColumnName = "id") // 外テーブルからJoinされる時のカラムはapp_user_idだよ
    @ManyToOne
    var group: Group? = null
}

@Embeddable
class AppUserGroupId(
        @Column(name="app_user_id")
        var appUserId: String? = null,

        @Column(name="group_id")
        var groupId: String? = null
): Serializable
