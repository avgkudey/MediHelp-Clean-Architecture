package com.teracode.medihelp.business.data.util

import android.util.Log
import com.teracode.medihelp.business.data.cache.CacheConstants.CACHE_TIMEOUT
import com.teracode.medihelp.business.data.cache.CacheErrors.CACHE_ERROR_TIMEOUT
import com.teracode.medihelp.business.data.cache.CacheErrors.CACHE_ERROR_UNKNOWN
import com.teracode.medihelp.business.data.cache.CacheResult
import com.teracode.medihelp.business.data.network.ApiResult
import com.teracode.medihelp.business.data.network.NetworkConstants.NETWORK_TIMEOUT
import com.teracode.medihelp.business.data.network.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.teracode.medihelp.business.data.network.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.teracode.medihelp.business.data.util.GenericErrors.ERROR_UNKNOWN
import com.teracode.medihelp.util.cLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT) {
                ApiResult.Success(apiCall.invoke())

            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Log.d("syncNetworkDru", "ApiResult: $throwable")
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    ApiResult.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    ApiResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    cLog(errorResponse)
                    ApiResult.GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    cLog(NETWORK_ERROR_UNKNOWN)
                    ApiResult.GenericError(
                        null,
                        NETWORK_ERROR_UNKNOWN
                    )
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {

                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    cLog(CACHE_ERROR_UNKNOWN)
                    CacheResult.GenericError(CACHE_ERROR_UNKNOWN)
                }
            }
        }
    }
}


private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }
}







