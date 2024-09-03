package de.kairenken.parzellenwetter.infrastructure.wetter.rest

import de.kairenken.parzellenwetter.domain.wetter.Wetter
import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/wetter")
class WetterController(private val wetterRepository: WetterRepository) {

    @GetMapping("/")
    fun getWetterBetween(
        @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime,
        @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: LocalDateTime
    ): ResponseEntity<List<ReadWetterDto>> =
        wetterRepository.holeWetter(von = from.plusHours(2L), bis = to.plusHours(2L))
            .map { it.toReadDto() }
            .wrapItInResponse()

    private fun Wetter.toReadDto() = ReadWetterDto(
        id = this.id,
        zeitpunkt = this.zeitpunkt,
        temperatur = this.temperatur,
        luftfeuchtigkeit = this.luftfeuchtigkeit,
        taupunkt = this.taupunkt,
        luftdruck = this.luftdruck,
        windrichtung = this.windrichtung,
        windgeschwindigkeit = this.windgeschwindigkeit,
        windboeengeschwindigkeit = this.windboeengeschwindigkeit,
        sonnenstrahlung = this.sonnenstrahlung,
        uvIndex = this.uvIndex,
        niederschlag = this.niederschlag,
        niederschlagGesamt = this.niederschlagGesamt
    )

    private fun List<ReadWetterDto>.wrapItInResponse(): ResponseEntity<List<ReadWetterDto>> =
        ResponseEntity.ok(this)
}
