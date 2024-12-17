package de.kairenken.parzellenwetter.infrastructure.notiz.rest.dto

data class CreateNotizDto(
    val verfasser: String,
    val nachricht: String
)