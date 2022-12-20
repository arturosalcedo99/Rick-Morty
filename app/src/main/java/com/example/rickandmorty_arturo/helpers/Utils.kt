package com.example.rickandmorty_arturo.helpers

import com.example.rickandmorty_arturo.data.source.remote.api.ApiError
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException

object Utils {
    inline fun <reified T : Any> Any.cast(): T {
        return this as T
    }

    fun getError(exception: Exception): ApiError {
        return when (exception) {
            is IOException -> ApiError.Network(exception.message)
            is UnknownHostException -> ApiError.Network(exception.message)
            is HttpException -> {
                when (exception.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ApiError.NotFound(exception.message)
                    HttpURLConnection.HTTP_BAD_REQUEST -> ApiError.BadRequest(exception.message)
                    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ApiError.Timeout(exception.message)
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> ApiError.Server(exception.message)
                    HttpURLConnection.HTTP_FORBIDDEN -> ApiError.Unauthorized(exception.message)
                    HttpURLConnection.HTTP_UNAVAILABLE -> ApiError.Unavailable(exception.message)
                    else -> ApiError.Unknown(exception.message)
                }
            }
            else -> ApiError.Unknown(exception.message)
        }
    }
}