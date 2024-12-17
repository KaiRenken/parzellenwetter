package de.kairenken.parzellenwetter.domain.notiz

interface NotizRepository {

    fun speichere(notiz: Notiz)
}