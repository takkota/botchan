package net.onlybiz.botchan.database

import org.hibernate.annotations.Cascade
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.Constraint
import javax.validation.constraints.NotNull


@Entity
@Table(
        name = "app_user_groups",
        uniqueConstraints = [
                UniqueConstraint(columnNames = ["app_user_id", "group_id"])
        ]
)
data class AppUserGroup(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        // AppUserは登録ずみの前提なので、CascadeTypeはAllにしない
        @ManyToOne
        @NotNull
        var appUser: AppUser,

        // Groupを新規で登録できるように
        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var group: Group,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity()
