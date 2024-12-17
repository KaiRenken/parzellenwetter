package de.kairenken.parzellenwetter.infrastructure.notiz.repository

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import de.kairenken.parzellenwetter.domain.notiz.Notiz
import de.kairenken.parzellenwetter.domain.notiz.NotizRepository
import de.kairenken.parzellenwetter.infrastructure.notiz.repository.entity.NotizEntity
import org.springframework.stereotype.Repository

@Repository
class NotizRepositoryImpl(private val notizJpaRepository: NotizJpaRepository) : NotizRepository {

    override fun speichere(notiz: Notiz) {
        notizJpaRepository.save(notiz.toEntity())
    }

    override fun holeAlle(): List<Notiz> = notizJpaRepository
        .findAll()
        .map { it.toDomain() }

    private fun Notiz.toEntity() = NotizEntity(
        id = this.id.value,
        verfasser = this.verfasser.value,
        nachricht = this.nachricht.value,
        zeitpunkt = this.zeitpunkt.value
    )

    private fun NotizEntity.toDomain(): Notiz = Notiz(
        id = Notiz.Id(this.id),
        verfasser = (Notiz.Verfasser(this.verfasser) as Erzeugt).value,
        nachricht = (Notiz.Nachricht(this.nachricht) as Erzeugt).value,
        zeitpunkt = Notiz.Zeitpunkt(this.zeitpunkt)
    )
}
