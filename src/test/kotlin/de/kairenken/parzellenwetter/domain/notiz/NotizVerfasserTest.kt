package de.kairenken.parzellenwetter.domain.notiz

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import de.kairenken.parzellenwetter.domain.common.UngueltigeArgumente
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Erzeuge Notiz.Verfasser")
internal class NotizVerfasserTest {

    @Test
    fun erfolgreich() {
        val notizVerfasser = Notiz.Verfasser(
            notizVerfasserFixture.value
        )

        notizVerfasser.shouldBeInstanceOf<Erzeugt<Notiz.Verfasser>>()
            .value shouldBe notizVerfasserFixture
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `mit leerem value`(value: String) {
        val notizVerfasser = Notiz.Verfasser(value)

        notizVerfasser.shouldBeInstanceOf<UngueltigeArgumente>()
            .messages.shouldContainExactlyInAnyOrder(
                "Notiz.Verfasser darf nicht leer sein"
            )
    }

    @Test
    fun `mit zu langem value`() {
        val notizVerfasser = Notiz.Verfasser("A".repeat(101))

        notizVerfasser.shouldBeInstanceOf<UngueltigeArgumente>()
            .messages.shouldContainExactlyInAnyOrder(
                "Notiz.Verfasser hat 101 Zeichen, darf aber höchstens 100 haben."
            )
    }
}