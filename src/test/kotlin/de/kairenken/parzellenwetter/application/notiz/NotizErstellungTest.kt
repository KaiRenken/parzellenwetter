package de.kairenken.parzellenwetter.application.notiz

import de.kairenken.parzellenwetter.domain.notiz.NotizRepository
import de.kairenken.parzellenwetter.domain.notiz.notizFixture
import de.kairenken.parzellenwetter.domain.notiz.notizNachrichtFixture
import de.kairenken.parzellenwetter.domain.notiz.notizVerfasserFixture
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Called
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Erstelle Notiz")
internal class NotizErstellungTest {

    private val notizRepositoryMock = mockk<NotizRepository>()

    private val notizErstellungToTest = NotizErstellung(notizRepositoryMock)

    @Test
    fun erfolgreich() {
        every { notizRepositoryMock.speichere(any()) } just Runs

        val erstellungsErgebnis = notizErstellungToTest.erstelleNotiz(
            verfasser = notizVerfasserFixture.value,
            nachricht = notizNachrichtFixture.value
        )

        erstellungsErgebnis.shouldBeInstanceOf<NotizErstellung.NotizErstellt>()
            .notiz shouldBe notizFixture.copy(
            id = erstellungsErgebnis.notiz.id,
            zeitpunkt = erstellungsErgebnis.notiz.zeitpunkt
        )
        verify(exactly = 1) {
            notizRepositoryMock.speichere(
                notizFixture.copy(
                    id = erstellungsErgebnis.notiz.id,
                    zeitpunkt = erstellungsErgebnis.notiz.zeitpunkt
                )
            )
        }
    }

    @Test
    fun `mit ungueltigen Argumenten`() {
        val erstellungsErgebnis = notizErstellungToTest.erstelleNotiz(
            verfasser = "",
            nachricht = ""
        )

        erstellungsErgebnis.shouldBeInstanceOf<NotizErstellung.UngueltigeArgumente>()
            .fehlermeldungen.shouldContainExactlyInAnyOrder(
                "Notiz.Nachricht darf nicht leer sein",
                "Notiz.Verfasser darf nicht leer sein"
            )
        verify { notizRepositoryMock wasNot Called }
    }
}