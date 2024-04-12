package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.upbit_api_response.TradeCenterDetailChartResponse
import com.clone_coding.domain.model.TradeCenterDetailChartModel

// 사실 이런 구조면 필요한가 싶음.
fun TradeCenterDetailChartResponse.toModel() = TradeCenterDetailChartModel(

    marketName = marketName,
    candleDateTimeUTC = candleDateTimeUTC,
    candleDateTimeKST = candleDateTimeKST,
    openingPrice = openingPrice,
    highPrice = highPrice,
    lowPrice = lowPrice,
    tradePrice = tradePrice,
    timestamp = timestamp,
    candleAccTradePrice = candleAccTradePrice,
    candleAccTradeVolume = candleAccTradeVolume,
    prevClosingPrice = prevClosingPrice,
    changePrice = changePrice,
    changeRate = changeRate,
    convertedTradePrice = convertedTradePrice

)

