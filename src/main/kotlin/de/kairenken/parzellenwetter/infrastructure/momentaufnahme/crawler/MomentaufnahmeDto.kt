package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.crawler

import java.time.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MomentaufnahmeDto(

    @SerialName("obsTimeLocal") var obsTimeLocal: String,

    @SerialName("solarRadiation") var solarRadiation: Float?,

    @SerialName("uv") var uv: Float?,

    @SerialName("winddir") var winddir: Int?,

    @SerialName("humidity") var humidity: Int?,

    @SerialName("metric") var metric: MetricDto?

) {
    @Serializable
    class MetricDto(

        @SerialName("temp") var temp: Int?,

        @SerialName("dewpt") var dewpt: Int?,

        @SerialName("windSpeed") var windSpeed: Int?,

        @SerialName("windGust") var windGust: Int?,

        @SerialName("pressure") var pressure: Float?,

        @SerialName("precipRate") var precipRate: Float?,

        @SerialName("precipTotal") var precipTotal: Float?
    )
}