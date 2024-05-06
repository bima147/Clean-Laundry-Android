package com.bimaprakoso.cleanlaundrybootcamp.domain

import com.bimaprakoso.cleanlaundrybootcamp.data.NetworkService
import com.bimaprakoso.cleanlaundrybootcamp.domain.model.User
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.BaseError
import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.mapToUser
import com.bimaprakoso.cleanlaundrybootcamp.utils.NetworkState
import com.bimaprakoso.cleanlaundrybootcamp.utils.parseError

class LoginRepository (private val service: NetworkService) {

    suspend fun postLogin(request: LoginRequest): NetworkState<User> {
        return try {
            val response = service.loginRequest(isGuest = true, login = request)
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    body.data?.mapToUser()?.let { user ->
                        NetworkState.Success(user)
                    } ?: run { NetworkState.Error(error = BaseError(error = "Null Response")) }
                } else {
                    parseError(response)
                }
            } else {
                parseError(response)
            }
        }catch (e: Exception) {
            NetworkState.Error(error = e)
        }
    }

}