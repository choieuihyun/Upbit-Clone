package com.clone_coding.data.mapper

import com.clone_coding.data.db.remote.response.coin_news_response.CoinInfoCoinNewsLatestNewsResponse
import com.clone_coding.domain.model.CoinInfoCoinNewsLatestNewsModel

fun CoinInfoCoinNewsLatestNewsResponse.toModel() = CoinInfoCoinNewsLatestNewsModel(

    newsTitle = newsTitle,

    newsHref = newsHref,

    newsTime = newsTime

)