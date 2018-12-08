package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PreUpdate
import javax.persistence.PrePersist


@MappedSuperclass
abstract class CommonEntity {
    @Column(name = "created")
    private var createdDatetime: Date? = null

    @Column(name = "modified")
    private var modifiedDateTime: Date? = null

    @PrePersist
    fun onPrePersist() {
        createdDatetime = Date()
        modifiedDateTime = Date()
    }

    @PreUpdate
    fun onPreUpdate() {
        modifiedDateTime = Date()
    }
}