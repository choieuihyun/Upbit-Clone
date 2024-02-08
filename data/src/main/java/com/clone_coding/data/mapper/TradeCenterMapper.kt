package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.TradeCenterKRWTabInitializePriceResponse
import com.clone_coding.data.db.remote.response.TradeCenterKRWTabMarketListResponse
import com.clone_coding.data.db.remote.response.UpbitMarketInformationResponse
import com.clone_coding.data.db.remote.response.UpbitMarketInformationResponseList
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TestModel
import com.clone_coding.domain.model.TradeCenterKRWTabInitializePriceModel
import com.clone_coding.domain.model.TradeCenterKRWTabMarketListModel
import com.clone_coding.domain.model.UpbitMarketInformationModel
import com.clone_coding.domain.model.UpbitMarketInformationModelList
import java.math.BigDecimal
import java.math.RoundingMode

fun TradeCenterKRWTabMarketListResponse.toModel() = TradeCenterKRWTabMarketListModel(

    tradeCenterMarketList = tradeCenterMarketList?.map { it.toModel() }

)

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

fun TradeCenterKRWTabInitializePriceModel.toTestModel() = TestModel(

    code = market,
    tradePrice = BigDecimal(tradePrice).setScale(1, RoundingMode.DOWN).toPlainString(),
    signedChangeRate = BigDecimal(signedChangeRate * 100).setScale(2, RoundingMode.DOWN).toPlainString(),
    accTradePrice24H = BigDecimal(accTradePrice24h / 1000000).setScale(0,RoundingMode.DOWN).toPlainString()

)


fun <T> NetworkResult<T>.toNetworkResult() : T =
    (this as NetworkResult.Success).data

private fun <R> changeNetworkData(replaceData: R) : NetworkResult<R> {
    return NetworkResult.Success(replaceData)
}

suspend fun <T, R> NetworkResult<T>.mapNetworkResult(getData: suspend (T) -> R): NetworkResult<R> {
    return changeNetworkData(getData(toNetworkResult()))
}