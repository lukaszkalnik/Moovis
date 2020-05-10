package com.lukaszkalnik.moovis.data.remote

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import okhttp3.Request
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Return type must be a parameterized type." }

        val rightType = getParameterUpperBound(1, responseType)
        return EitherCallAdapter<Any?>(rightType)
    }
}

class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<ApiError, R?>>> {

    override fun adapt(call: Call<R?>): Call<Either<ApiError, R?>> = EitherCall(call)

    override fun responseType(): Type = successType
}

internal class EitherCall<R>(
    private val delegate: Call<R?>
) : Call<Either<ApiError, R?>> {

    override fun enqueue(callback: Callback<Either<ApiError, R?>>) = delegate.enqueue(
        object : Callback<R?> {

            override fun onResponse(call: Call<R?>, response: Response<R?>) {
                with(response) {
                    if (isSuccessful) {
                        callback.onResponse(this@EitherCall, Response.success(Right(body())))
                    } else {
                        val errorBody = errorBody()?.string() ?: ""
                        callback.onResponse(
                            this@EitherCall,
                            Response.success(Left(HttpError(code(), errorBody)))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<R?>, throwable: Throwable) {
                val error = when (throwable) {
                    is IOException -> NetworkError(throwable)
                    else -> UnknownApiError(throwable)
                }
                callback.onResponse(this@EitherCall, Response.success(Left(error)))
            }
        }
    )

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<Either<ApiError, R?>> = EitherCall(delegate.clone())

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Either<ApiError, R?>> = throw UnsupportedOperationException()

    override fun request(): Request = delegate.request()
}
