package com.clone_coding.data.db.remote.response.upbit_api_response

import com.google.gson.annotations.SerializedName

data class CoinInfoCoinTrendHighestIncreaseRateResponse (

    @SerializedName("market") val market: String,

    @SerializedName("candle_date_time_utc") val candleDateTimeUTC: String,

    @SerializedName("candle_date_time_kst") val candleDateTimeKST: String,

    @SerializedName("trade_price") val tradePrice: Double,

    @SerializedName("opening_price") val openingPrice: Double

        )