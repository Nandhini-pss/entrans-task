package com.example.testapps.baseclass
import java.lang.Exception

sealed class Result<out T: Any, out U: Any> {
    data class Success<out T : Any>(val data: T) : Result<T, Nothing>()
    data class ApiError<out U: Any>(val data: U) : Result<Nothing, U>()
    data class LoginRedirect(val msg: String) : Result<Nothing, Nothing>()
    data class Error(val exception: Exception) : Result<Nothing, Nothing>()
}