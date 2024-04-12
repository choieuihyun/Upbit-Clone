package com.clone_coding.data.db.remote.response.upbit_api_response

import com.google.gson.annotations.SerializedName

data class TradeCenterDetailChartResponse(

    // 마켓명
    @SerializedName("market")
    val marketName: String,

    // 캔들 기준 시각(UTC 기준)
    @SerializedName("candle_date_time_utc")
    val candleDateTimeUTC: String,

    // 캔들 기준 시각(KST 기준)
    @SerializedName("candle_date_time_kst")
    val candleDateTimeKST: String,

    // 시가
    @SerializedName("opening_price")
    val openingPrice: Double,

    // 고가
    @SerializedName("high_price")
    val highPrice: Double,

    // 저가
    @SerializedName("low_price")
    val lowPrice: Double,

    // 종가
    @SerializedName("trade_price")
    val tradePrice: Double,

    // 마지막 틱이 저장된 시각
    @SerializedName("timestamp")
    val timestamp: Long,

    // 누적 거래 금액
    @SerializedName("candle_acc_trade_price")
    val candleAccTradePrice: Double,

    // 누적 거래량
    @SerializedName("candle_acc_trade_volume")
    val candleAccTradeVolume: Double,

    // 전일 종가(UTC 0시 기준)
    @SerializedName("prev_closing_price")
    val prevClosingPrice: Double,

    // 전일 종가 대비 변화 금액
    @SerializedName("change_price")
    val changePrice: Double,

    // 전일 종가 대비 변화량
    @SerializedName("change_rate")
    val changeRate: Double,

    // 종가 환산 화폐 단위로 환산된 가격
    @SerializedName("converted_trade_price")
    val convertedTradePrice: Double

)