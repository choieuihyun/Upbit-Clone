package com.clone_coding.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDatasource
import com.clone_coding.data.mapper.mapNetworkResult
import com.clone_coding.data.mapper.toModel
import com.clone_coding.data.mapper.toNetworkResult
import com.clone_coding.data.mapper.toTestModel
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TradeCenterKRWTabInitializePriceModel
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.domain.repository.UpbitTradeCenterRepository
import javax.inject.Inject

class UpbitTradeCenterRepositoryImpl @Inject constructor(
    private val datasource: UpbitTradeCenterDatasource
) : UpbitTradeCenterRepository {

    override val code: LiveData<String>
        get() = datasource.code

    override val tradePrice: LiveData<String>
        get() = datasource.tradePrice

    override val signedChangeRate: LiveData<String>
        get() = datasource.signedChangeRate

    override val accTradePrice24H: LiveData<String>
        get() = datasource.accTradePrice24H


    override suspend fun getTradeCenterRealTimeInfo() {

        datasource.connectWebSocket()

    }

    // KRW 탭 통합 데이터
//    override suspend fun getTradeCenterMarketList(): NetworkResult<TradeCenterKRWTabMarketListModel> {
//
//        val a = datasource.getTradeCenterMarketList()
//
//        val b = a.mapNetworkResult { it ->
//            it.toModel()
//        }
//
//        Log.d("repoImpl", b.toNetworkResult().tradeCenterMarketList.toString())
//
//        return b
//
//    }

    override suspend fun getTradeCenterCurrentPrice(): NetworkResult<List<TradeCenterKRWTabModel>?> {

        val a = datasource.getTradeCenterCurrentPrice()

        val b = a.mapNetworkResult { list ->
            list?.map {
                it.toModel().toTestModel()
            }
        }

        return b


    }


}