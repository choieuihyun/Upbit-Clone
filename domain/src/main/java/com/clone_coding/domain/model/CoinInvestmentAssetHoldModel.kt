package com.clone_coding.domain.model

data class CoinInvestmentAssetHoldModel(

    // 화폐를 의미하는 영문 대문자 코드 ex) BTC, XRP, ETH
    val currency: String,

    // 주문가능 금액/수량
    val balance: String,

    // 주문 중 묶여있는 금액/수량
    val locked: String,

    // 매수평균가
    val avgBuyPrice: String,

    // 매수평균가 수정 여부
    val avgBuyPriceModified: Boolean,

    // 평단가 기준 화폐
    val unitCurrency: String,

    // 총 보유자산 = 총매수 + 보유KRW + 평가손익
    val holdAsset: String,

    // 총매수 = 모든 자산 (balance * avgBuyPrice)
    val totalPurchase: String,

    // 총평가 = 모든 자산 (balance * 현재가)
    val totalEvaluation: String,

    // 평가손익 = 총평가 - 총매수
    val evaluationProfitLoss: String,

    // 수익률 = 평가손익 / 총매수
    val profitLossPercentage: String



)