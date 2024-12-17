package de.kairenken.parzellenwetter.infrastructure.notiz.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "notiz")
data class NotizEntity(

    @Id
    var id: UUID,

    @Column(name = "verfasser")
    var verfasser: String,

    @Column(name = "nachricht")
    var nachricht: String,

    @Column(name = "zeitpunkt")
    var zeitpunkt: LocalDateTime
)