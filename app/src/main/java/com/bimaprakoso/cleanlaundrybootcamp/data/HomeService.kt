package com.bimaprakoso.cleanlaundrybootcamp.data

import com.bimaprakoso.cleanlaundrybootcamp.domain.response.HomeResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface HomeService {
    @GET("/api/v1/users/get-data")
    @Headers("Cache-Control: max-age=640000", "User-Agent: Clean-Laundry-App")
    fun getHome(
        @Header("Authorization") token: String
    ): Call<ResponseDetail<HomeResponse>>
}