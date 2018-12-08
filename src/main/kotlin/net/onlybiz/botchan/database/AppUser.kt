package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "app_users")
class AppUser(
        @Id
        @Column(name = "app_user_id")
        var id: String,

        @Column(name = "line_id")
        var lineId: String? = null,

        var nonce: String? = null,

        @Column(name = "link_date_time")
        var linkDateTime: Date? = null
) : CommonEntity()
