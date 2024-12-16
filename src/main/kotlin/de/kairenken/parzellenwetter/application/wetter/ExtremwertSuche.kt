package de.kairenken.parzellenwetter.application.wetter

import de.kairenken.parzellenwetter.application.wetter.dto.ExtremwerteDto
import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class ExtremwertSuche(private val wetterRepository: WetterRepository) {

    fun holeExtremwerte(von: LocalDateTime, bis: LocalDateTime): ExtremwerteDto {
        val extremTemperaturen = wetterRepository.holeExtremTemperaturen(von = von, bis = bis)

        return ExtremwerteDto(
            extremTemperaturen = extremTemperaturen
        )
    }
}