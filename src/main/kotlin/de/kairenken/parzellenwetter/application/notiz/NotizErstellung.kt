package de.kairenken.parzellenwetter.application.notiz

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import de.kairenken.parzellenwetter.domain.notiz.Notiz
import de.kairenken.parzellenwetter.domain.notiz.NotizRepository
import org.springframework.stereotype.Service

@Service
class NotizErstellung(private val notizRepository: NotizRepository) {

    fun erstelleNotiz(verfasser: String, nachricht: String): Ergebnis = when (val notizErzeugungsErgebnis = Notiz(
        verfasser = verfasser,
        nachricht = nachricht
    )) {
        is Erzeugt -> {
            notizRepository.speichere(notizErzeugungsErgebnis.value)
            NotizErstellt(notizErzeugungsErgebnis.value)
        }

        is de.kairenken.parzellenwetter.domain.common.UngueltigeArgumente -> UngueltigeArgumente(
            notizErzeugungsErgebnis.messages
        )
    }

    sealed class Ergebnis
    class NotizErstellt(val notiz: Notiz) : Ergebnis()
    class UngueltigeArgumente(val fehlermeldungen: List<String>) : Ergebnis()
}