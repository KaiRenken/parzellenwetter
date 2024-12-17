package de.kairenken.parzellenwetter.domain.notiz

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import de.kairenken.parzellenwetter.domain.common.ErzeugungsErgebnis
import de.kairenken.parzellenwetter.domain.common.UngueltigeArgumente
import de.kairenken.parzellenwetter.domain.common.ValidationUtil
import de.kairenken.parzellenwetter.domain.common.ValidationUtil.Companion.validate
import java.time.LocalDateTime
import java.util.UUID

data class Notiz(
    val id: Id = Id(),
    val verfasser: Verfasser,
    val nachricht: Nachricht,
    val zeitpunkt: Zeitpunkt = Zeitpunkt()
) {

    data class Id(val value: UUID = UUID.randomUUID())

    data class Verfasser private constructor(val value: String) {
        companion object {
            operator fun invoke(name: String): ErzeugungsErgebnis<Verfasser> = when (val validationResult = validate {
                require(name.isNotBlank()) { "Notiz.Verfasser darf nicht leer sein" }
                require(name.length <= 100) { "Notiz.Verfasser hat ${name.length} Zeichen, darf aber höchstens 100 haben." }
            }) {
                is ValidationUtil.Error -> UngueltigeArgumente(validationResult.errors)
                ValidationUtil.Success -> Erzeugt(Verfasser(name))
            }
        }

        fun change(value: String = this.value) = this.copy(value = value)
    }

    data class Nachricht private constructor(val value: String) {
        companion object {
            operator fun invoke(value: String): ErzeugungsErgebnis<Nachricht> = when (val validationResult = validate {
                require(value.isNotBlank()) { "Notiz.Nachricht darf nicht leer sein" }
                require(value.length <= 1000) { "Notiz.Nachricht hat ${value.length} Zeichen, darf aber höchstens 1000 haben." }
            }) {
                is ValidationUtil.Error -> UngueltigeArgumente(validationResult.errors)
                ValidationUtil.Success -> Erzeugt(Nachricht(value))
            }
        }

        fun change(value: String = this.value) = this.copy(value = value)
    }

    data class Zeitpunkt(val value: LocalDateTime = LocalDateTime.now())

    companion object {
        operator fun invoke(
            verfasser: String,
            nachricht: String
        ): ErzeugungsErgebnis<Notiz> = when (val validationResult = validate(
            Verfasser(verfasser),
            Nachricht(nachricht)
        )) {
            is ValidationUtil.Error -> UngueltigeArgumente(validationResult.errors)
            ValidationUtil.Success -> Erzeugt(
                Notiz(
                    verfasser = (Verfasser(verfasser) as Erzeugt).value,
                    nachricht = (Nachricht(nachricht) as Erzeugt).value
                )
            )
        }
    }
}