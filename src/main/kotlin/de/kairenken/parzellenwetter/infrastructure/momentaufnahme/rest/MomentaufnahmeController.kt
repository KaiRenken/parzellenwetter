package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.rest

import de.kairenken.parzellenwetter.domain.momentaufnahme.Momentaufnahme
import de.kairenken.parzellenwetter.domain.momentaufnahme.MomentaufnahmeRepository
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/momentaufnahme")
class MomentaufnahmeController(private val momentaufnahmeRepository: MomentaufnahmeRepository) {

    @GetMapping("/")
    fun getMomentaufnahmenBetween(
        @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime,
        @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: LocalDateTime
    ): ResponseEntity<List<ReadMomentaufnahmeDto>> =
        momentaufnahmeRepository.holeMomentaufnahmen(von = from, bis = to)
            .map { it.toReadDto() }
            .wrapItInResponse()

    private fun Momentaufnahme.toReadDto() = ReadMomentaufnahmeDto(
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

    private fun List<ReadMomentaufnahmeDto>.wrapItInResponse(): ResponseEntity<List<ReadMomentaufnahmeDto>> =
        ResponseEntity.ok(this)
}
