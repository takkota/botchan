package net.onlybiz.botchan.database

import javax.persistence.*


@Entity
@Table(name = "tests")
class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String? = null

    var address: String? = null
}
