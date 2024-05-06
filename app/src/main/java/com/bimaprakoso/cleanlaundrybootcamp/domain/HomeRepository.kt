package com.bimaprakoso.cleanlaundrybootcamp.domain

import com.bimaprakoso.cleanlaundrybootcamp.data.NetworkService
import com.bimaprakoso.cleanlaundrybootcamp.domain.model.Home
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.BaseError
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.HomeResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.mapToHome
import com.bimaprakoso.cleanlaundrybootcamp.utils.NetworkState
import com.bimaprakoso.cleanlaundrybootcamp.utils.parseError

class HomeRepository(private val service: NetworkService) {
    suspend fun getDataHome(token: String): NetworkState<Home> {
        return try {
            val response = service.getHome(isGuest = false, token = token)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    body.data.mapToHome()?.let {home ->
                        NetworkState.Success(home)
                    } ?:
                    run {
                        NetworkState
                            .Error(error = BaseError(error = "Null Response"))
                    }
                }else {
                    parseError(response)
                }
            } else {
                parseError(response)
            }
        }catch (e:Exception) {
            NetworkState
                .Error(error = e)
        }
    }
}