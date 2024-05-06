package com.bimaprakoso.cleanlaundrybootcamp.data

import android.telecom.Call
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ListItemResponse
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.ResponseList
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionService {
    @GET("/api/v1/transaction/customer/list/{page}/{sort}/{sortBy}")
    @Headers("Cache-Control: max-age=640000", "User-Agent: Clean-Laundry-App")
    suspend fun getAllTransaction(
        @Header("Authorization") token: String,
        @Path("page") page: Int,
        @Path("sort") sort: String,
        @Path("sortBy") sortBy: String,
        @Query("filterBy") filterBy: String,
        @Query("value") value: String,
        @Query("size") size: Int
    ): Response<ResponseList<TransactionResponse>>
}