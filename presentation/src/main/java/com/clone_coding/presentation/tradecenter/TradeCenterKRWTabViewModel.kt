package com.clone_coding.presentation.tradecenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.domain.repository.UpbitTradeCenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeCenterKRWTabViewModel @Inject constructor(
    private val repository: UpbitTradeCenterRepository
) : ViewModel() {

    // 현재가(Ticker) 데이터

    // 마켓 코드 (ex. KRW-BTC)
    private val _code = MutableLiveData<String>()
    val code: LiveData<String>
        get() = _code

    // 현재가
    private val _tradePrice = MutableLiveData<String>()
    val tradePrice: LiveData<String>
        get() = _tradePrice

    // 전일 대비 등락율
    private val _signedChangeRate = MutableLiveData<String>()
    val signedChangeRate: LiveData<String>
        get() = _signedChangeRate

    // 24시간 누적 거래대금
    private val _accTradePrice24H = MutableLiveData<String>()
    val accTradePrice24H: LiveData<String>
        get() = _accTradePrice24H

    // krwTab 전체 데이터
    private val _marketData = MutableLiveData<List<TradeCenterKRWTabModel>>()
    val marketData: LiveData<List<TradeCenterKRWTabModel>>
        get() =  _marketData

    fun getTradeCenterRealTimeInfo() {

        viewModelScope.launch {

            repository.getTradeCenterRealTimeInfo()

            when (val result = repository.getTradeCenterCurrentPrice()) {
                is NetworkResult.Success -> {
                    val marketDataList = result.data

                    val initializeMarketData =
                        mutableListOf<TradeCenterKRWTabModel>()

                    if (marketDataList != null) {
                        for (marketData in marketDataList) {
                            initializeMarketData.add(
                                TradeCenterKRWTabModel(
                                    marketData.code,
                                    marketData.tradePrice,
                                    marketData.signedChangeRate,
                                    marketData.accTradePrice24H
                                )
                            )
                        }
                        _marketData.value = initializeMarketData.sortedByDescending {
                            it.accTradePrice24H?.toDoubleOrNull() ?: 0.0
                        }
                        Log.d("viewModelMessage", marketData.value.toString())
                    }
                }

                is NetworkResult.Error -> Log.d("viewModelMessage2", "error")
            }


            repository.accTradePrice24H.observeForever { accTradePrice24H ->

                val currentMarketData = _marketData.value ?: emptyList()

                // 중복된 코드인지 확인
                val existingItemIndex =
                    currentMarketData.indexOfFirst { it.code == repository.code.value }

                // 중복된 코드가 있는 경우 해당 아이템을 업데이트하고, 그렇지 않은 경우 새로운 아이템 추가
                val updatedMarketData = if (existingItemIndex != -1) {
                    // 중복된 코드가 있는 경우 해당 아이템을 업데이트

                    val updatedData = currentMarketData.toMutableList()
                    updatedData[existingItemIndex] = TradeCenterKRWTabModel(
                        repository.code.value ?: "",
                        repository.tradePrice.value ?: "",
                        repository.signedChangeRate.value ?: "",
                        repository.accTradePrice24H.value ?: ""
                    )
                    updatedData
                } else {
                    // 중복된 코드가 없는 경우 새로운 아이템 추가
                    currentMarketData + TradeCenterKRWTabModel(
                        repository.code.value ?: "",
                        repository.tradePrice.value ?: "",
                        repository.signedChangeRate.value ?: "",
                        repository.accTradePrice24H.value ?: ""
                    )
                }

                _marketData.value = updatedMarketData.sortedByDescending {
                    it.accTradePrice24H?.toDoubleOrNull() ?: 0.0
                }
            }
        }
    }

}




