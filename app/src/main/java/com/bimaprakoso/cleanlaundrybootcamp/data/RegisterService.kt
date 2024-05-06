package com.bimaprakoso.cleanlaundrybootcamp.data

import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.request.RegisterRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.LoginResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("/api/v1/users/auth/register")
    fun registerRequest(
        @Body register: RegisterRequest
    ): Call<ResponseDetail<String>>
}