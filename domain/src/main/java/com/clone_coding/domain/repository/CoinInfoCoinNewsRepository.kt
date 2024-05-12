package com.clone_coding.domain.repository

import com.clone_coding.domain.model.CoinInfoCoinNewsLatestNewsModel

interface CoinInfoCoinNewsRepository {

    val mainNewsTitle: String

    val mainNewsSummary: String

    val subNewsMap: Map<String, String>

    val coinNewsList : MutableList<CoinInfoCoinNewsLatestNewsModel>

    val policyNewsList : MutableList<CoinInfoCoinNewsLatestNewsModel>

    val techNewsList : MutableList<CoinInfoCoinNewsLatestNewsModel>

    val columnNewsList : MutableList<CoinInfoCoinNewsLatestNewsModel>

    suspend fun getMainNews()

    suspend fun getLatestCoinNews()

    suspend fun getLatestPolicyNews()

    suspend fun getLatestTechNews()

    suspend fun getLatestColumnNews()

}