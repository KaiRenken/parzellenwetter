package de.kairenken.parzellenwetter.application.wetter

import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import org.springframework.stereotype.Service

@Service
class WetterUpdate(private val wetterRepository: WetterRepository) {

    fun updateWetter() {
        val neuesWetter = wetterRepository.holeAktuellesWetter()

        wetterRepository.speichereWetter(neuesWetter)
    }
}
