package com.azuyamat.mccollector.collectors

/**
 * Represents a verifiable collector.
 *
 * @since 1.0.0
 */
interface Verifiable {
    fun verifyValue(value: String): ValidationResult
    data class ValidationResult internal constructor(val isValid: Boolean, val errorMessage: String? = null) {
        companion object {
            fun valid(message: String? = null): ValidationResult {
                return ValidationResult(true, message)
            }

            fun invalid(errorMessage: String? = null): ValidationResult {
                return ValidationResult(false, errorMessage)
            }
        }
    }
}