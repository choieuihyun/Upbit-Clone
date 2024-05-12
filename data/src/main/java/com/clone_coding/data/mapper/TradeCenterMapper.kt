package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.upbit_api_response.TradeCenterKRWTabInitializePriceResponse
import com.clone_coding.data.db.remote.response.upbit_api_response.TradeCenterKRWTabMarketListResponse
import com.clone_coding.data.db.remote.response.upbit_api_response.UpbitMarketInformationResponse
import com.clone_coding.data.db.remote.response.upbit_api_response.UpbitMarketInformationResponseList
import com.clone_coding.domain.model.TradeCenterKRWTabInitializePriceModel
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.domain.model.UpbitMarketInformationModel
import com.clone_coding.domain.model.UpbitMarketInformationModelList
import java.math.BigDecimal
import java.math.RoundingMode


fun UpbitMarketInformationResponseList.toModel() = UpbitMarketInformationModelList(

    upbitMarketInformationModelList = upbitMarketInformationResponseList.body()?.map { it.toModel() }

)

fun UpbitMarketInformationResponse.toModel() = UpbitMarketInformationModel(
    market = market,
    koreanName = koreanName,
    englishName = englishName,
    marketWarning = marketWarning
)

// 거래소 KRW 탭 초기 데이터
fun TradeCenterKRWTabInitializePriceResponse.toModel() = TradeCenterKRWTabInitializePriceModel(
    market = market,
    tradePrice = tradePrice,
    signedChangeRate = signedChangeRate,
    accTradePrice24h = accTradePrice24h
)

fun TradeCenterKRWTabInitializePriceModel.toTestModel() = TradeCenterKRWTabModel(

    code = market,
    tradePrice = BigDecimal(tradePrice).setScale(1, RoundingMode.DOWN).toPlainString(),
    signedChangeRate = BigDecimal(signedChangeRate * 100).setScale(2, RoundingMode.DOWN).toPlainString(),
    accTradePrice24H = BigDecimal(accTradePrice24h / 1000000).setScale(0,RoundingMode.DOWN).toPlainString()

)


