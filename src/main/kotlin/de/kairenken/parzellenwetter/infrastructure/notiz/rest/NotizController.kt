package de.kairenken.parzellenwetter.infrastructure.notiz.rest

import de.kairenken.parzellenwetter.application.notiz.NotizErstellung
import de.kairenken.parzellenwetter.domain.notiz.Notiz
import de.kairenken.parzellenwetter.domain.notiz.NotizRepository
import de.kairenken.parzellenwetter.infrastructure.common.ErrorResponseDto
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.dto.CreateNotizDto
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.dto.ReadNotizDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notiz")
class NotizController(
    private val notizErstellung: NotizErstellung,
    private val notizRepository: NotizRepository
) {
    @PostMapping("/")
    fun createNotiz(@RequestBody createNotizDto: CreateNotizDto) =
        when (val notizErstellungsErgebnis = notizErstellung.erstelleNotiz(
            verfasser = createNotizDto.verfasser,
            nachricht = createNotizDto.nachricht
        )) {
            is NotizErstellung.NotizErstellt -> ResponseEntity.ok(notizErstellungsErgebnis.notiz.toReadDto())
            is NotizErstellung.UngueltigeArgumente -> ResponseEntity(
                ErrorResponseDto(notizErstellungsErgebnis.fehlermeldungen),
                HttpStatus.BAD_REQUEST
            )
        }

    @GetMapping("/")
    fun getNotizen(): ResponseEntity<List<ReadNotizDto>> =
        notizRepository.holeAlle()
            .map { it.toReadDto() }
            .wrapItInOkResponse()

    private fun Notiz.toReadDto() = ReadNotizDto(
        id = this.id.value,
        verfasser = this.verfasser.value,
        nachricht = this.nachricht.value,
        zeitpunkt = this.zeitpunkt.value
    )

    private fun List<ReadNotizDto>.wrapItInOkResponse() = ResponseEntity.ok(this)
}
