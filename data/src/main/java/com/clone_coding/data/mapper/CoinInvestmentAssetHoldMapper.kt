package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.coin_investment_asset_hold_response.CoinInvestmentAssetHoldResponse
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel

fun CoinInvestmentAssetHoldResponse.toModel(currentPrice: String) = CoinInvestmentAssetHoldModel(

    // 화폐를 의미하는 영문 대문자 코드 ex) BTC, XRP, ETH, KRW(원화)
    currency = currency,

    // 주문가능 금액/수량
    balance = balance,

    // 주문 중 묶여있는 금액/수량
    locked = locked,

    // 매수평균가
    avgBuyPrice = avgBuyPrice,

    // 매수평균가 수정 여부
    avgBuyPriceModified = avgBuyPriceModified,

    // 평단가 기준 화폐 (내가 구매할 때 사용한 화폐 ex) KRW, BTC, USDT)
    unitCurrency = unitCurrency,

    // 총 보유자산 = 총매수 + 평가손익 + 보유KRW (근데 보유KRW는 currency로 받아오는거라 여기서 더하지 못한다.)
    holdAsset = holdAsset(

        totalPurchase(balance, avgBuyPrice),

        evaluationProfitLoss(

            totalEvaluation(balance, currentPrice),
            totalPurchase(balance, avgBuyPrice)

        )

    ),

    // 총매수 = 모든 자산 (balance * avgBuyPrice)
    totalPurchase = totalPurchase(balance, avgBuyPrice),

    // 총평가 = 모든 자산 (balance * 현재가)
    totalEvaluation = totalEvaluation(balance, currentPrice),

    // 평가손익 = 총평가 - 총매수
    evaluationProfitLoss = evaluationProfitLoss(
        totalEvaluation(balance, currentPrice),
        totalPurchase(balance, avgBuyPrice)
    ),

    // 수익률 = 평가손익 / 총매수
    profitLossPercentage = profitLossPercentage(
        evaluationProfitLoss(
            totalEvaluation(balance, currentPrice),
            totalPurchase(balance, avgBuyPrice)
        ),
        totalPurchase(balance, avgBuyPrice)
    )


)

// 아래는 두 가지 구조로 따로 구현해놓았다. 무엇이 선호되는지는 취향차이인가??

private val totalPurchase: (String, String) -> String = { balance, avgBuyPrice ->

    (balance.toDouble() * avgBuyPrice.toDouble()).toInt().toString()

}

private val totalEvaluation: (String, String) -> String = { balance, currentPrice ->

    (balance.toDouble() * currentPrice.toDouble()).toInt().toString()

}

private val evaluationProfitLoss: (String, String) -> String = { totalEvaluation, totalPurchase ->

    (totalEvaluation.toDouble() - totalPurchase.toDouble()).toInt().toString()

}

private val profitLossPercentage: (String, String) -> String =
    { evaluationProfitLoss, totalPurchase ->

        (evaluationProfitLoss.toDouble() / totalPurchase.toDouble()).toString()

    }

private val holdAsset: (String, String) -> String = { totalPurchase, evaluationProfitLoss ->

    (totalPurchase.toDouble() + evaluationProfitLoss.toDouble()).toInt().toString()

}

fun calculateTotalPurchase(balance: String, avgBuyPrice: String): String {

    return (balance.toDouble() * avgBuyPrice.toDouble()).toString()

}

fun calculateTotalEvaluation(balance: String, currentPrice: String): String {

    return (balance.toDouble() * currentPrice.toDouble()).toString()

}

fun calculateEvaluationProfitLoss(totalEvaluation: String, totalPurchase: String): String {

    return (totalEvaluation.toDouble() - totalPurchase.toDouble()).toString()

}

fun calculateProfitLossPercentage(evaluationProfitLoss: String, totalPurchase: String): String {

    return (evaluationProfitLoss.toDouble() / totalPurchase.toDouble()).toString()

}