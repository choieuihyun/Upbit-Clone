package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.coin_investment_asset_hold_response.CoinInvestmentAssetHoldResponse
import com.clone_coding.data.db.remote.response.coingecko_api_response.CoinAllDataResponse
import com.clone_coding.domain.model.CoinWithdrawCoinListModel
import com.clone_coding.domain.model.CoinWithdrawModel
import com.clone_coding.domain.model.MarketCapListModel

// 이건 애초에 하면 안되는 구조같음. 각각 다른 서버에서 다른 API 형식으로 보내주는데 어떻게 이걸 너가 다룰 수 있냐.
// 애초에 순서부터 안맞아서 순차적으로 넣으려고 하면 데이터가 안맞는다.
//fun mapToWithdrawModel(coinAllDataResponse: CoinAllDataResponse, assetHold: CoinInvestmentAssetHoldResponse) = CoinWithdrawModel(
//
//    coinBalance = assetHold.balance ?: "",
//
//    coinName = assetHold.currency ?: "",
//
//    coinSymbol = coinAllDataResponse.image ?: "",
//
//    coinTotalPrice = (assetHold.avgBuyPrice.toDouble() * assetHold.balance.toDouble()).toString()
//
//)

fun CoinAllDataResponse.toWithdrawModel(coinAllDataResponse: CoinAllDataResponse) = CoinWithdrawCoinListModel(

    // 이 세개는 assetHold에서 받아온 데이터를 넣을 예정. 위의 주석처럼 하면 당연히 안됨
    // 같은 서버에서 같은 형식으로 내려주는 데이터면 가능할 수도 있다고 생각은 드는데..
    symbol = "",

    coinTotalPrice = "",

    coinBalance = "",

    name = coinAllDataResponse.name,

    imageUrl = coinAllDataResponse.image,

    currentPrice = coinAllDataResponse.currentPrice.toString()

)

fun CoinAllDataResponse.toWithDrawCoinListModel() = MarketCapListModel(

    symbol = symbol,
    currentPrice = currentPrice.toString(),
    name = name,
    imageUrl = image,
    marketCap = (marketCap / 100000000).toInt(),
    marketCapRank = marketCapRank,
    marketCapChange24h = (marketCapChange24h / 100000000).toInt()

)
