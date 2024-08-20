package com.clone_coding.data.di

import com.clone_coding.data.BuildConfig
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
    private val secretKey = System.getenv("secret_key")

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UpbitApiRetrofit // 동적 URL 할당을 위한 annotation class

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CoinGeckoApiRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class JwtTokenOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UpbitJwtTokenRetrofit

    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.DAYS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            //.addNetworkInterceptor(interceptor) 뭐야 이게 필요도 없는데 에러 일으킴.
            .build()
    }

    // 이거 jwtToken 부분 절대 커밋하지 말자.
    @Singleton
    @Provides
    @JwtTokenOkHttpClient
    fun provideJwtTokenOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.DAYS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(JwtInterceptor(BuildConfig.UPBIT_API_KEY, ""))
            .build()
    }

    @Singleton
    @Provides
    @UpbitApiRetrofit
    fun provideUpbitRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(UpbitAPI_BASE_URL)
            .build()

    }

    @Singleton
    @Provides
    @UpbitJwtTokenRetrofit
    fun provideUpbitJwtRetrofit(@JwtTokenOkHttpClient okHttpClient: OkHttpClient): Retrofit {

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

//    @Singleton
//    @Provides
//    fun provideWebSocketInterceptor(webSocketClient: WebSocketClient,
//                                    webSocketListener: TradeCenterWebSocketListener): WebSocketInterceptor {
//        return WebSocketInterceptor(webSocketClient, webSocketListener, UpbitAPI_WEBSOCKET_BASE_URL)
//    }

//    @Singleton
//    @Provides
//    fun provideWebSocketManager(
//        webSocketListener: TradeCenterWebSocketListener,
//        webSocketUrl: String,
//        webSocketClient: WebSocketClient
//    ): WebSocketManager {
//        return WebSocketManager(webSocketClient, webSocketListener, webSocketUrl)
//    }

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