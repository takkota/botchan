package net.onlybiz.botchan.database

import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(
        name = "app_user_line_group",
        uniqueConstraints = [
                UniqueConstraint(columnNames = ["app_user_id", "line_group_id"])
        ]
)
data class AppUserLineGroup(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        // AppUserは登録ずみの前提なので、CascadeTypeはAllにしない
        @ManyToOne
        @NotNull
        var appUser: AppUser? = null,

        // LineGroupを新規で登録できるように
        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var lineGroup: LineGroup? = null,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity()
