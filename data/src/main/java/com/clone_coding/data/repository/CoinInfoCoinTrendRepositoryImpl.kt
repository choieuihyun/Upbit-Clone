package com.clone_coding.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.clone_coding.data.datasource.remote_datasource.CoinInfoCoinTrendDatasource
import com.clone_coding.data.mapper.mapNetworkResult
import com.clone_coding.data.mapper.toModel

import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.MarketCapListModel
import com.clone_coding.domain.model.CoinInfoCoinTrendHighestIncreaseRateModel
import com.clone_coding.domain.repository.CoinInfoCoinTrendRepository
import javax.inject.Inject

class CoinInfoCoinTrendRepositoryImpl @Inject constructor(
    private val coinTrendDatasource: CoinInfoCoinTrendDatasource,
) : CoinInfoCoinTrendRepository {

    // 마켓 코드 (ex. KRW-BTC)
    override val market: LiveData<String>
        get() = coinTrendDatasource.code

    // 매도 체결량
    override val accAskVolume: LiveData<String>
        get() = coinTrendDatasource.accAskVolume

    // 매수 체결량
    override val accBidVolume: LiveData<String>
        get() = coinTrendDatasource.accBidVolume

//    override val volumePowerRate: MediatorLiveData<Pair<String?, String?>> = MediatorLiveData()

    override suspend fun getCoinInfoCoinTrendRealTimeInfo() {

        // coinTrendDatasource.connectWebSocket()

    }



//    override suspend fun getCoinInfoCoinTrendVolumePower() {
//
//        volumePowerRate.addSource(accAskVolume) { askVolume ->
//
//            val bidVolume = accBidVolume.value
//
//            if (askVolume != null && bidVolume != null) // 첫번째 데이터가 계속 null이 관찰됨 ㅠㅠ
//                volumePowerRate.value = Pair(askVolume, bidVolume)
//        }
//
//        volumePowerRate.addSource(accBidVolume) { bidVolume ->
//
//            val askVolume = accAskVolume.value
//
//            if (askVolume != null && bidVolume != null)
//                volumePowerRate.value = Pair(askVolume, bidVolume)
//        }
//
//    }

    override suspend fun getCoinInfoCoinTrendHighestIncreaseRate(): NetworkResult<List<List<CoinInfoCoinTrendHighestIncreaseRateModel>?>> {

        Log.d("repoImplAccAskVolume", accAskVolume.value.toString())
        Log.d("repoImplAccBidVolume", accBidVolume.value.toString())

        val coinTrendHighestIncreaseRateResponse =
            coinTrendDatasource.getCoinInfoCoinTrendHighestIncreaseRate()

        val coinTrendHighestIncreaseRateModel =
            coinTrendHighestIncreaseRateResponse.mapNetworkResult { list ->
                list.map { responseList ->
                    responseList?.map {
                        it.toModel()
                    }
                }
            }

        return coinTrendHighestIncreaseRateModel
    }

    override suspend fun getCoinMarketCapList(): NetworkResult<List<List<MarketCapListModel>?>> {

        val coinMarketCapListResponse = coinTrendDatasource.getCoinMarketCapList()

        val coinMarketCapListModel = coinMarketCapListResponse.mapNetworkResult { list ->
            list.map { responseList ->
                responseList?.map {
                    it.toModel()
                }
            }
        }

        return coinMarketCapListModel
    }
}