package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "wetter")
class WetterEntity(

    @Id
    var id: UUID,

    @Column(name = "zeitpunkt")
    var zeitpunkt: LocalDateTime,

    @Column(name = "sonnenstrahlung")
    var sonnenstrahlung: Float?,

    @Column(name = "uv_index")
    var uvIndex: Float?,

    @Column(name = "windrichtung")
    var windrichtung: Int?,

    @Column(name = "luftfeuchtigkeit")
    var luftfeuchtigkeit: Int?,

    @Column(name = "temperatur")
    var temperatur: Int?,

    @Column(name = "taupunkt")
    var taupunkt: Int?,

    @Column(name = "windgeschwindigkeit")
    var windgeschwindigkeit: Int?,

    @Column(name = "windboeengeschwindigkeit")
    var windboeengeschwindigkeit: Int?,

    @Column(name = "luftdruck")
    var luftdruck: Float?,

    @Column(name = "niederschlag")
    var niederschlag: Float?,

    @Column(name = "niederschlag_gesamt")
    var niederschlagGesamt: Float?
)