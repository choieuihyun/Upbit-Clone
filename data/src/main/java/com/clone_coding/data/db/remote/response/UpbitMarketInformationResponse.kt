package com.clone_coding.data.db.remote.response

import com.google.gson.annotations.SerializedName

// 마켓 정보 및 Ticker 전체 조회
class UpbitMarketInformationResponse (

    // 종목 구분 코드 (예: "KRW-BTC", "KRW-ETH")
    // 시세 호출의 파라미터
    @SerializedName("market") val market: String,

    // 코인 한글명
    @SerializedName("korean_name") val koreanName: String,

    // 코인 영문명
    @SerializedName("english_name") val englishName: String

    )