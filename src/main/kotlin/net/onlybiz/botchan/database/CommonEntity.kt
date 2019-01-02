package net.onlybiz.botchan.database

import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PreUpdate
import javax.persistence.PrePersist

@MappedSuperclass
abstract class CommonEntity {
    @Column(name = "created", updatable = false)
    private var createdDatetime: Date = Date()

    @Column(name = "modified", updatable = true)
    private var modifiedDateTime: Date = Date()

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