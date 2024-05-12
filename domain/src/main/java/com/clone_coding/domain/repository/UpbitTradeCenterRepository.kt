package com.clone_coding.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TradeCenterKRWTabInitializePriceModel
import com.clone_coding.domain.model.TradeCenterKRWTabModel

interface UpbitTradeCenterRepository {

    // 아무리 봐도 여기서 Livedata 사용 아니라고 생각한다.

    // 마켓 코드 (ex. KRW-BTC)
    val code: LiveData<String>

    // 현재가
    val tradePrice: LiveData<String>

    // 전일 대비 등락율
    val signedChangeRate: LiveData<String>

    // 24시간 누적 거래대금
    val accTradePrice24H: LiveData<String>

    // 마켓 코드 조회 https://docs.upbit.com/v1.4.0/reference/마켓-코드-조회
    // val tradeCenterMarketList: LiveData<List<String>>

    suspend fun getTradeCenterRealTimeInfo()

    // 마켓 코인 리스트
    // suspend fun getTradeCenterMarketList(): NetworkResult<TradeCenterKRWTabMarketListModel>

    suspend fun getTradeCenterCurrentPrice(): NetworkResult<List<TradeCenterKRWTabModel>?>


}