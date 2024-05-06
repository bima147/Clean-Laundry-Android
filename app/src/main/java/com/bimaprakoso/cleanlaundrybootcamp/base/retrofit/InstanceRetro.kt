package com.bimaprakoso.cleanlaundrybootcamp.base.retrofit

import android.util.Log
import com.bimaprakoso.cleanlaundrybootcamp.base.interceptor.HeaderInterceptor
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.bimaprakoso.cleanlaundrybootcamp.base.interceptor.SSLPinning
import com.bimaprakoso.cleanlaundrybootcamp.data.HomeService
import com.bimaprakoso.cleanlaundrybootcamp.data.LoginService
import com.bimaprakoso.cleanlaundrybootcamp.data.NetworkService
import com.bimaprakoso.cleanlaundrybootcamp.data.RegisterService
import com.bimaprakoso.cleanlaundrybootcamp.data.TransactionService
import com.bimaprakoso.cleanlaundrybootcamp.domain.HomeRepository
import com.bimaprakoso.cleanlaundrybootcamp.domain.LoginRepository
import com.bimaprakoso.cleanlaundrybootcamp.presentation.factory.HomeViewModelFactory
import com.bimaprakoso.cleanlaundrybootcamp.presentation.factory.LoginViewModelFactory
import com.bimaprakoso.cleanlaundrybootcamp.utils.ApplicationProviderUtils
import com.bimaprakoso.cleanlaundrybootcamp.utils.DataStoreUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

object InstanceRetro {
    private const val BASE_URL = "http://10.0.2.2:8082" // Ganti dengan URL API Anda

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    val registerService: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

    val homeService: HomeService by lazy {
        retrofit.create(HomeService::class.java)
    }

    val listTransaction: TransactionService by lazy {
        retrofit.create(TransactionService::class.java)
    }
}