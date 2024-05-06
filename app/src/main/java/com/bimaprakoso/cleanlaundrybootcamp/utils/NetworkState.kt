package com.bimaprakoso.cleanlaundrybootcamp.utils

import com.bimaprakoso.cleanlaundrybootcamp.domain.response.BaseError
import com.google.gson.Gson
import retrofit2.Response

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val error: Exception): NetworkState<Nothing>()
}

fun <T : Any>parseError(error: Response<T>?): NetworkState.Error {
    return try {
        NetworkState.Error(Gson().fromJson(error?.errorBody()?.string(), BaseError::class.java))
    } catch (e: Exception) {
        NetworkState.Error(BaseError(error = e.message.orEmpty()))
    }
}