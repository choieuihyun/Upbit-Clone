package com.clone_coding.data.db.remote.response.coin_investment_asset_hold_response

import com.google.gson.annotations.SerializedName

data class CoinInvestmentAssetHoldResponse(

    // 내가 산 코인 티커
    @SerializedName("currency") val currency: String,

    // 주문가능 금액/수량
    @SerializedName("balance") val balance: String,

    // 주문 중 묶여있는 금액/수량
    @SerializedName("locked") val locked: String,

    // 매수 평균가
    @SerializedName("avg_buy_price") val avgBuyPrice: String,

    // 매수 평균가 수정 여부
    @SerializedName("avg_buy_price_modified") val avgBuyPriceModified: Boolean,

    // 평단가 기준 화폐
    @SerializedName("unit_currency") val unitCurrency: String
)

