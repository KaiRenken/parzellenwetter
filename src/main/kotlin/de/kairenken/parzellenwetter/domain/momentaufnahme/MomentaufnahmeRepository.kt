package de.kairenken.parzellenwetter.domain.momentaufnahme

import java.time.LocalDateTime

interface MomentaufnahmeRepository {

    fun holeMomentaufnahmen(von: LocalDateTime, bis: LocalDateTime): List<Momentaufnahme>
}