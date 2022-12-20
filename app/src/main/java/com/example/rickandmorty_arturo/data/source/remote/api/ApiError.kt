package com.example.rickandmorty_arturo.data.source.remote.api

object Empty: ApiError()

sealed class ApiError(val errorMessage: String? = null): Exception() {
    class Network(errorMessage: String?): ApiError(errorMessage)
    class NotFound(errorMessage: String?): ApiError(errorMessage)
    class BadRequest(errorMessage: String?): ApiError(errorMessage)
    class Timeout(errorMessage: String?): ApiError(errorMessage)
    class Server(errorMessage: String?): ApiError(errorMessage)
    class Unauthorized(errorMessage: String?): ApiError(errorMessage)
    class Unavailable(errorMessage: String?): ApiError(errorMessage)
    class Unknown(errorMessage: String?): ApiError(errorMessage)
}