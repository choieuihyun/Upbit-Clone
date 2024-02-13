package com.clone_coding.data.db.remote.api

import com.clone_coding.data.db.remote.response.coingecko_api_response.CoinAllDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getCoinAllDataList(
        @Query("vs_currency") vs_currency: String,
        @Query("ids") ids: String,
        @Query("order") order: String,
        @Query("per_page") per_page: String,
        @Query("page") page: String,
        @Query("price_change_percentage") price_change_percentage: String,
        @Query("locale") locale: String
    ): Response<List<CoinAllDataResponse>> // Response에서 List를 만들거나 여기서 List로 반환하거나.

}