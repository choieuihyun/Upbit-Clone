package com.clone_coding.domain.model

data class CoinWithdrawModel(

    // 코인 심볼
    val symbol: String,

    // 현재가
    val currentPrice: String,

    // 코인명
    val name: String,

    // 이미지 url
    val imageUrl: String,

    // 코인 시가총액
    val marketCap: Int,

    // 코인 시가총액 순위
    val marketCapRank: Int,

    // 거래금(24h)
    val marketCapChange24h: Int,

    // 여기까지가 코인 마켓캡 데이터---------------------------

    val coinName: String,

    val coinSymbol: String,

    val coinTotalPrice: String,

    val coinBalance: String

)