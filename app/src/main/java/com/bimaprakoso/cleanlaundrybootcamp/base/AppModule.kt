package com.bimaprakoso.cleanlaundrybootcamp.base

import android.util.Log
import com.bimaprakoso.cleanlaundrybootcamp.base.interceptor.HeaderInterceptor
import com.bimaprakoso.cleanlaundrybootcamp.base.interceptor.SSLPinning
import com.bimaprakoso.cleanlaundrybootcamp.data.NetworkService
import com.bimaprakoso.cleanlaundrybootcamp.domain.HomeRepository
import com.bimaprakoso.cleanlaundrybootcamp.domain.LoginRepository
import com.bimaprakoso.cleanlaundrybootcamp.presentation.factory.HomeViewModelFactory
import com.bimaprakoso.cleanlaundrybootcamp.presentation.factory.LoginViewModelFactory
import com.bimaprakoso.cleanlaundrybootcamp.utils.ApplicationProviderUtils
import com.bimaprakoso.cleanlaundrybootcamp.utils.DataStoreUtils
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {
    private const val BASE_URL = "http://10.0.2.2:8082" // Ganti dengan URL API Anda

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        connectTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
        certificatePinner(SSLPinning.getPinnedCertificate())
//        addInterceptor(MockInterceptor())
        addInterceptor(ChuckerInterceptor(ApplicationProviderUtils.get()))
        addInterceptor(HeaderInterceptor(DataStoreUtils.get()))
        addInterceptor(HttpLoggingInterceptor {
                message -> Log.d("Http log", message)
        })
    }.build()

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun provideNetworkService() : NetworkService {
        return provideRetrofit().create(NetworkService::class.java)
    }

    private fun provideLoginRepository(): LoginRepository = LoginRepository(provideNetworkService())

    private fun provideHomeRepository(): HomeRepository = HomeRepository(provideNetworkService())

    val loginViewModelFactory = LoginViewModelFactory(provideLoginRepository())

    val homeViewModelFactory = HomeViewModelFactory(provideHomeRepository())
}