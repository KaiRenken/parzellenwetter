package de.kairenken.parzellenwetter.domain.momentaufnahme

import java.time.LocalDateTime
import org.springframework.stereotype.Repository

@Repository
interface MomentaufnahmeRepository {

    fun holeMomentaufnahmen(von: LocalDateTime, bis: LocalDateTime): List<Momentaufnahme>

    fun holeAktuelleMomentaufnahme(): Momentaufnahme

    fun speichereMomentaufnahme(momentaufnahme: Momentaufnahme)
}