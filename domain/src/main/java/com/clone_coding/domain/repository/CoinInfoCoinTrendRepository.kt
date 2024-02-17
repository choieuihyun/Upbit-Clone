package com.clone_coding.domain.repository

import androidx.lifecycle.LiveData
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.MarketCapListModel
import com.clone_coding.domain.model.CoinInfoCoinTrendHighestIncreaseRateModel

interface CoinInfoCoinTrendRepository {

    // 코인 티커
    val market: LiveData<String>

    // 누적 매도량
    val accAskVolume: LiveData<String>

    // 누적 매수량
    val accBidVolume: LiveData<String>

    // 체결량
//    val volumePowerRate: MediatorLiveData<Pair<String?, String?>>

    suspend fun getCoinInfoCoinTrendRealTimeInfo()

    suspend fun getCoinMarketCapList(): NetworkResult<List<List<MarketCapListModel>?>> // 반환타입 진짜 맘에안듬.

//    suspend fun getCoinInfoCoinTrendVolumePower()

    suspend fun getCoinInfoCoinTrendHighestIncreaseRate(): NetworkResult<List<List<CoinInfoCoinTrendHighestIncreaseRateModel>?>>



}