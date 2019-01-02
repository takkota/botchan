package net.onlybiz.botchan.database

import javax.persistence.*


@Entity
@Table(name = "test")
class Test {
    @Id
    @Column(name = "replyConditionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String? = null

    var address: String? = null
}
