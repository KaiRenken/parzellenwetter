package de.kairenken.parzellenwetter.infrastructure.notiz

import de.kairenken.parzellenwetter.domain.notiz.Notiz
import de.kairenken.parzellenwetter.domain.notiz.NotizRepository
import de.kairenken.parzellenwetter.infrastructure.notiz.entity.NotizEntity
import org.springframework.stereotype.Repository

@Repository
class NotizRepositoryImpl(private val notizJpaRepository: NotizJpaRepository) : NotizRepository {

    override fun speichere(notiz: Notiz) {
        notizJpaRepository.save(notiz.toEntity())
    }

    private fun Notiz.toEntity() = NotizEntity(
        id = this.id.value,
        verfasser = this.verfasser.value,
        nachricht = this.nachricht.value,
        zeitpunkt = this.zeitpunkt.value
    )
}
