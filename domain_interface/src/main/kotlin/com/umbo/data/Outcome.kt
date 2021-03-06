package com.umbo.data

sealed class Outcome<out T : Any> {
    data class Success<out T : Any>(val value: T) : Outcome<T>()
    data class Error(val cause: Exception? = null) : Outcome<Nothing>()
}