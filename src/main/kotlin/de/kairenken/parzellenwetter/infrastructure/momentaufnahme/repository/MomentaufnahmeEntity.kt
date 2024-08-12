package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "momentaufnahme")
class MomentaufnahmeEntity(

    @Id
    var id: UUID,

    @Column(name = "obs_time_local")
    var obsTimeLocal: LocalDateTime,

    @Column(name = "solar_radiation")
    var solarRadiation: Float?,

    @Column(name = "uv")
    var uv: Float?,

    @Column(name = "winddir")
    var winddir: Int?,

    @Column(name = "humidity")
    var humidity: Int?,

    @Column(name = "temp")
    var temp: Int?,

    @Column(name = "dewpt")
    var dewpt: Int?,

    @Column(name = "wind_speed")
    var windSpeed: Int?,

    @Column(name = "wind_gust")
    var windGust: Int?,

    @Column(name = "pressure")
    var pressure: Float?,

    @Column(name = "percip_rate")
    var precipRate: Float?,

    @Column(name = "percip_total")
    var precipTotal: Float?
)