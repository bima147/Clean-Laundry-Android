package com.bimaprakoso.cleanlaundrybootcamp.data

import com.bimaprakoso.cleanlaundrybootcamp.domain.request.LoginRequest
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.LoginResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.HomeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {
    @GET("/api/v1/users/get-data")
    fun getHome(
        @Header("is_guest") isGuest: Boolean = true,
        @Header("Authorization") token: String
    ): Response<ResponseDetail<HomeResponse>>

    @POST("/api/v1/users/auth/login")
    fun loginRequest(
        @Header("is_guest") isGuest: Boolean = true,
        @Body login: LoginRequest
    ): Response<ResponseDetail<LoginResponse>>
}