package com.bimaprakoso.cleanlaundrybootcamp.data

import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.LoginResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/users/auth/login")
    fun loginRequest(
        @Body login: LoginRequest
    ): Call<ResponseDetail<LoginResponse>>
}