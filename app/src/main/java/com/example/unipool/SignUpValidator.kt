package com.example.unipool

object SignUpValidator {

    data class ValidationResult(val isValid: Boolean, val errorMessage: String = "")

    fun validateUsername(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult(false, "Username is required")
            username.length < 3 -> ValidationResult(false, "Username must be at least 3 characters")
            else -> ValidationResult(true)
        }
    }

    fun validateEmail(email: String, domain: String? = null): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email is required")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                ValidationResult(false, "Enter a valid email address")
            domain != null && !email.endsWith(domain) ->
                ValidationResult(false, "Email must end with $domain")
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "Password is required")
            password.length < 8 -> ValidationResult(false, "Password must be at least 8 characters")
            else -> ValidationResult(true)
        }
    }

    fun validateConfirmPassword(password: String, confirm: String): ValidationResult {
        return when {
            confirm.isBlank() -> ValidationResult(false, "Please confirm your password")
            password != confirm -> ValidationResult(false, "Passwords do not match")
            else -> ValidationResult(true)
        }
    }

    fun validateShuttleId(shuttleId: String): ValidationResult {
        return when {
            shuttleId.isBlank() -> ValidationResult(false, "Shuttle ID is required")
            else -> ValidationResult(true)
        }
    }
}