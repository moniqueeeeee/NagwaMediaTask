package com.monica.nagwamediatask.di.modules


import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(90, TimeUnit.SECONDS)
        clientBuilder.readTimeout(90, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}