package com.clone_coding.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDatasource
import com.clone_coding.data.mapper.mapNetworkResult
import com.clone_coding.data.mapper.toModel
import com.clone_coding.data.mapper.toNetworkResult
import com.clone_coding.data.mapper.toTestModel
import com.clone_coding.domain.error.NetworkError
import com.clone_coding.domain.error.NetworkResult
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

        return when (a) {
            is NetworkResult.Success -> {
                val b = a.data?.map { it.toModel().toTestModel() }
                NetworkResult.Success(b)
            }
            is NetworkResult.Error -> {
                // 네트워크 호출에서 에러가 발생한 경우 여기서 적절히 처리
                Log.d("sdfsdfErrorImpl", a.toString())
                a // 그대로 반환
            }
            else -> {
                // 예상치 못한 NetworkResult 타입인 경우
                Log.d("sdfsdfErrorImpl2", a.toString())
                NetworkResult.Error(NetworkError.Unknown)
            }
        }

/*        val a = datasource.getTradeCenterCurrentPrice()
        Log.d("sdfsdfErrorImpl", a.toString())
        val b = a.mapNetworkResult { list ->

            list?.map {
                it.toModel().toTestModel()
            }
        }

        return b*/


    }


}