package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.coingecko_api_response.CoinAllDataResponse
import com.clone_coding.data.db.remote.response.upbit_api_response.CoinInfoCoinTrendHighestIncreaseRateResponse
import com.clone_coding.domain.model.MarketCapListModel
import com.clone_coding.domain.model.CoinInfoCoinTrendHighestIncreaseRateModel

fun CoinInfoCoinTrendHighestIncreaseRateResponse.toModel() = CoinInfoCoinTrendHighestIncreaseRateModel(

    market = market,
    candleDateTimeUTC = candleDateTimeUTC,
    candleDateTimeKST = candleDateTimeKST,
    tradePrice = tradePrice,
    openingPrice = openingPrice

)

fun CoinAllDataResponse.toModel() = MarketCapListModel(

    symbol = symbol,
    name = name,
    imageUrl = image,
    marketCap = (marketCap / 100000000).toInt(),
    marketCapRank = marketCapRank,
    marketCapChange24h = (marketCapChange24h / 100000000).toInt()

)