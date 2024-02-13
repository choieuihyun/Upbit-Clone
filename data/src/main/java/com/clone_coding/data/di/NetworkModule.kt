package com.clone_coding.data.di

import com.clone_coding.data.db.remote.interactor.NetworkErrorHandlerImpl
import com.clone_coding.domain.error.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val UpbitAPI_BASE_URL = "https://api.upbit.com/v1/"
    private const val CoinGeckoAPI_BASE_URL = "https://api.coingecko.com/api/v3/"
    private const val UpbitAPI_WEBSOCKET_BASE_URL = "wss://api.upbit.com/websocket/v1"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UpbitApiRetrofit // 동적 URL 할당을 위한 annotation class

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CoinGeckoApiRetrofit

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.DAYS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(UpbitAPI_BASE_URL)
            .build()

    }

    @Singleton
    @Provides
    @CoinGeckoApiRetrofit
    fun provideCoinGeckoRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(CoinGeckoAPI_BASE_URL)
            .build()

    }

    @Singleton
    @Provides
    fun provideTradeCenterWebSocketListener(): WebSocketListener {
        return TradeCenterWebSocketListener()
    }

    @Singleton
    @Provides
    fun provideCoinInfoCoinTrendWebSocketListener(): WebSocketListener {
        return CoinInfoCoinTrendWebSocketListener()
    }

    @Singleton
    @Provides
    fun provideWebSocketInterceptor(webSocketClient: WebSocketClient,
                                    webSocketListener: TradeCenterWebSocketListener): WebSocketInterceptor {
        return WebSocketInterceptor(webSocketClient, webSocketListener, UpbitAPI_WEBSOCKET_BASE_URL)
    }

    @Singleton
    @Provides
    fun provideYourWebSocketClient(): WebSocketClient {
        return WebSocketClient()
    }

    @Singleton
    @Provides
    fun provideWebSocketUrl(): String {
        return UpbitAPI_WEBSOCKET_BASE_URL
    }

    @Singleton
    @Provides // 굳이 싱글톤일 필요가 있나?
    fun provideNetworkHandler(): NetworkErrorHandler {
        return NetworkErrorHandlerImpl()
    }



}