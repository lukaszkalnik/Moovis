package com.lukaszkalnik.moovis.data.remote

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Either::class.java) return null

        returnType as ParameterizedType
        val responseType = getParameterUpperBound(1, returnType)
        return EitherCallAdapter<Any>(responseType)
    }
}

class EitherCallAdapter<R>(
    private val responseType: Type
) : CallAdapter<R, Either<Exception, R>> {

    override fun adapt(call: Call<R>): Either<Exception, R> = try {
        with(call.execute()) {
            // Success or server error
            if (isSuccessful) Right(body()!!) else Left(Exception(errorBody()?.string() ?: "Unknown error"))
        }
    } catch (exception: Exception) {
        // Client error - e.g. no internet connection
        Left(exception)
    }

    override fun responseType(): Type = responseType
}