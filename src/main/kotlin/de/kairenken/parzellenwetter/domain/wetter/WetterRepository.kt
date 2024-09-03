package de.kairenken.parzellenwetter.domain.wetter

import java.time.LocalDateTime
import org.springframework.stereotype.Repository

@Repository
interface WetterRepository {

    fun holeWetter(von: LocalDateTime, bis: LocalDateTime): List<Wetter>

    fun holeAktuellesWetter(): Wetter

    fun speichereWetter(wetter: Wetter)
}