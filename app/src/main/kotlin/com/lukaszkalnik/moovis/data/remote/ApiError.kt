package com.lukaszkalnik.moovis.data.remote

/**
 * Error hierarchy when calling remote server.
 */
sealed class ApiError

/**
 * Http request returned an error response.
 */
data class HttpError(val code: Int, val body: String) : ApiError()

/**
 * Network error on client.
 */
data class NetworkError(val throwable: Throwable) : ApiError()

/**
 * Unknown error.
 */
data class UnknownApiError(val throwable: Throwable) : ApiError()
