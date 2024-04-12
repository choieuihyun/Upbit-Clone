package com.clone_coding.domain.model

// 여기서 차트에 필요한대로 모델을 만들까, 그냥 그대로 사용할까 고민하던 중
// viewModel에서 다시 한 번 데이터 클래스를 만들어서 매핑하기로 했다.
data class TradeCenterDetailChartModel(

    // 마켓명
    val marketName: String,

    // 캔들 기준 시각(UTC 기준)
    val candleDateTimeUTC: String,

    // 캔들 기준 시각(KST 기준)
    val candleDateTimeKST: String,

    // 시가
    val openingPrice: Double,

    // 고가
    val highPrice: Double,

    // 저가
    val lowPrice: Double,

    // 종가
    val tradePrice: Double,

    // 마지막 틱이 저장된 시각
    val timestamp: Long,

    // 누적 거래 금액
    val candleAccTradePrice: Double,

    // 누적 거래량
    val candleAccTradeVolume: Double,

    // 전일 종가(UTC 0시 기준)
    val prevClosingPrice: Double,

    // 전일 종가 대비 변화 금액
    val changePrice: Double,

    // 전일 종가 대비 변화량
    val changeRate: Double,

    // 종가 환산 화폐 단위로 환산된 가격
    val convertedTradePrice: Double

)