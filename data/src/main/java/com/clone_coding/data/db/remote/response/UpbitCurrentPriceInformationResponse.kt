package com.clone_coding.data.db.remote.response

import com.google.gson.annotations.SerializedName

class UpbitCurrentPriceInformationResponse (

    // 종목 구분 코드 (예: "KRW-BTC", "KRW-ETH")
    @SerializedName("market") val market: String,

    // 최근 거래 일자(UTC), 포맷: yyyyMMdd
    @SerializedName("trade_date") val tradeDate: String,

    // 최근 거래 시각(UTC), 포맷: HHmmss
    @SerializedName("trade_time") val tradeTime: String,

    // 최근 거래 일자(KST), 포맷: yyyyMMdd
    @SerializedName("trade_date_kst") val tradeDateKST: String,

    // 최근 거래 시각(KST), 포맷: HHmmss
    @SerializedName("trade_time_kst") val tradeTimeKST: String,

    // 최근 거래 일시(UTC), 포맷: Unix Timestamp
    @SerializedName("trade_timestamp") val tradeTimestamp: Long,

    // 시가
    @SerializedName("opening_price") val openingPrice: Double,

    // 고가
    @SerializedName("high_price") val highPrice: Double,

    // 저가
    @SerializedName("low_price") val lowPrice: Double,

    // 종가(현재가)
    @SerializedName("trade_price") val tradePrice: Double,

    // 전일 종가(UTC 0시 기준)
    @SerializedName("prev_closing_price") val prevClosingPrice: Double,

    // 변화 상태 (EVEN: 보합, RISE: 상승, FALL: 하락)
    @SerializedName("change") val change: String,

    // 변화액의 절대값
    @SerializedName("change_price") val changePrice: Double,

    // 변화율의 절대값
    @SerializedName("change_rate") val changeRate: Double,

    // 부호가 있는 변화액
    @SerializedName("signed_change_price") val signedChangePrice: Double,

    // 부호가 있는 변화율
    @SerializedName("signed_change_rate") val signedChangeRate: Double,

    // 가장 최근 거래량
    @SerializedName("trade_volume") val tradeVolume: Double,

    // 누적 거래대금(UTC 0시 기준)
    @SerializedName("acc_trade_price") val accTradePrice: Double,

    // 24시간 누적 거래대금
    @SerializedName("acc_trade_price_24h") val accTradePrice24h: Double,

    // 누적 거래량(UTC 0시 기준)
    @SerializedName("acc_trade_volume") val accTradeVolume: Double,

    // 24시간 누적 거래량
    @SerializedName("acc_trade_volume_24h") val accTradeVolume24h: Double,

    // 52주 신고가
    @SerializedName("highest_52_week_price") val highest52WeekPrice: Double,

    // 52주 신고가 달성일, 포맷: yyyy-MM-dd
    @SerializedName("highest_52_week_date") val highest52WeekDate: String,

    // 52주 신저가
    @SerializedName("lowest_52_week_price") val lowest52WeekPrice: Double,

    // 52주 신저가 달성일, 포맷: yyyy-MM-dd
    @SerializedName("lowest_52_week_date") val lowest52WeekDate: String,

    // 타임스탬프
    @SerializedName("timestamp") val timestamp: Long
)

// 거래서 KRW 탭 Response
class KRWTabResponse(

    // 종목 구분 코드 (예: "KRW-BTC", "KRW-ETH")
    @SerializedName("market") val market: String,

    // 종가(현재가)
    @SerializedName("trade_price") val tradePrice: Double,

    // 부호가 있는 변화율
    @SerializedName("signed_change_rate") val signedChangeRate: Double,

    // 24시간 누적 거래대금
    @SerializedName("acc_trade_price_24h") val accTradePrice24h: Double,

)