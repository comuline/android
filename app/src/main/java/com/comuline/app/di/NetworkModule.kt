package com.comuline.app.di

import com.comuline.app.BuildConfig
import com.comuline.app.network.ScheduleApi
import com.comuline.app.network.StationsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://www.api.comuline.com/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideStationsService(retrofit: Retrofit): StationsApi =
        retrofit.create(StationsApi::class.java)

    @Provides
    @Singleton
    fun provideScheduleService(retrofit: Retrofit): ScheduleApi =
        retrofit.create(ScheduleApi::class.java)

}