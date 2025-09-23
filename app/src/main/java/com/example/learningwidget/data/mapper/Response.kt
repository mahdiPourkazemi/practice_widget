package com.example.learningwidget.data.mapper

import com.example.learningwidget.utils.ErrorType

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val exception: ErrorType) :  Response<Nothing>() //todo consider adding string
}