package com.clone_coding.domain.model

import java.io.Serializable

data class TradeCenterKRWTabModel(

    // 마켓 코드 (ex. KRW-BTC)
    val code: String?,

    // 현재가
    val tradePrice: String?,

    // 전일 대비 등락율
    val signedChangeRate: String?,

    // 24시간 누적 거래대금
    val accTradePrice24H: String?

) : Serializable

data class UpbitMarketInformationModelList(

    val upbitMarketInformationModelList: List<UpbitMarketInformationModel>?

)

data class UpbitMarketInformationModel(

    // 종목 구분 코드 (예: "KRW-BTC", "KRW-ETH")
    // 시세 호출의 파라미터
    val market: String?,

    // 코인 한글명
    val koreanName: String?,

    // 코인 영문명
    val englishName: String?,

    val marketWarning: String

)

data class TradeCenterKRWTabInitializePriceModel (

    // 종목 구분 코드 (예: "KRW-BTC", "KRW-ETH")
    val market: String,

    // 종가(현재가)
    val tradePrice: Double,

    // 부호가 있는 변화율
    val signedChangeRate: Double,

    // 24시간 누적 거래대금
    val accTradePrice24h: Double

    )

