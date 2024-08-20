package de.kairenken.parzellenwetter.application.momentaufnahme

import de.kairenken.parzellenwetter.domain.momentaufnahme.MomentaufnahmeRepository
import org.springframework.stereotype.Service

@Service
class MomentaufnahmenUpdate(private val momentaufnahmeRepository: MomentaufnahmeRepository) {

    fun updateMomentaufnahmen() {
        val neueMomentaufnahme = momentaufnahmeRepository.holeAktuelleMomentaufnahme()

        momentaufnahmeRepository.speichereMomentaufnahme(neueMomentaufnahme)
    }
}