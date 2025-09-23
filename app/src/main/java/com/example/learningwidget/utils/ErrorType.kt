package com.example.learningwidget.utils

sealed class ErrorType {
    data class DatabaseError(val cause: Throwable? = null) : ErrorType()
    data class NetworkError(val cause: Throwable? = null) : ErrorType()
    data class ValidationError(val message: String) : ErrorType()
    data class UnknownError(val message: String, val cause: Throwable? = null) : ErrorType()

    // متد برای دریافت پیام کاربرپسند
    fun getUserFriendlyMessage(): String {
        return when (this) {
            is DatabaseError -> "Failed to access database"
            is NetworkError -> "Network connection failed"
            is ValidationError -> message
            is UnknownError -> message
        }
    }
}