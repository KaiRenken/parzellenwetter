package de.kairenken.parzellenwetter.domain.common

class ValidationUtil private constructor(
    private val errors: MutableList<String> = ArrayList(),
) {
    fun require(
        checker: Boolean,
        message: () -> String,
    ) {
        if (!checker) errors.add(message.invoke())
    }

    companion object {
        fun validate(block: ValidationUtil.() -> Unit): ValidationResult {
            val validationUtil = ValidationUtil()
            validationUtil.apply(block)

            if (validationUtil.errors.isEmpty()) return Success

            return Error(errors = validationUtil.errors.toList())
        }

        fun validate(vararg erzeugungsErgebnisse: ErzeugungsErgebnis<*>?): ValidationResult {
            val validationUtil = ValidationUtil()

            erzeugungsErgebnisse.forEach {
                when (it) {
                    is Erzeugt -> {}
                    is UngueltigeArgumente -> validationUtil.errors.addAll(it.messages)
                    null -> {}
                }
            }

            if (validationUtil.errors.isEmpty()) return Success

            return Error(errors = validationUtil.errors.toList())
        }
    }

    sealed class ValidationResult
    data object Success : ValidationResult()
    class Error(val errors: List<String>) : ValidationResult()
}