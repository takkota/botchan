package net.onlybiz.botchan.database

import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(
        name = "app_user_room",
        uniqueConstraints = [
                UniqueConstraint(columnNames = ["app_user_id", "room_id"])
        ]
)
data class AppUserRoom(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        // AppUserは登録ずみの前提なので、CascadeTypeはAllにしない
        @ManyToOne
        @NotNull
        var appUser: AppUser? = null,

        // Roomを新規で登録できるように
        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var room: Room? = null,

        @Column(name="display_name")
        var displayName: String? = null

) : CommonEntity()
