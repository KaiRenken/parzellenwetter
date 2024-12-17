package de.kairenken.parzellenwetter.domain.common

sealed class ErzeugungsErgebnis<out T>

class Erzeugt<T>(val value: T) : ErzeugungsErgebnis<T>()

class UngueltigeArgumente(val messages: List<String> = emptyList()) : ErzeugungsErgebnis<Nothing>()