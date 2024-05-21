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

    // 데이터 분류 타입
    // 이와 같은 경우는 객체 내부의 데이터를 통한 분류가 이루어져야했기 때문에 그걸 나누는 것이 어려워 사용.
    enum class SortType {
        AMOUNT, // 거래대금
        COMPARE_PREVIOUS_DAY, // 전일대비
        CURRENT_PRICE // 현재가
    }

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
        get() = _marketData

    var transactionAmountState = false // 거래대금 클릭 상태

    var comparePreviousDayState = false // 전일대비 클릭 상태

    var currentPriceState = false // 현재가 클릭 상태

    var currentSortType = SortType.AMOUNT // 초기 데이터는 거래대금 내림차순

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

                // 해당하는 로딩 화면 만들어야함.
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

                updateMarketDataState(updatedMarketData, currentSortType, getCurrentSortState())

            }
        }
    }

    private fun updateMarketDataState(
        list: List<TradeCenterKRWTabModel>,
        sortType: SortType,
        state: Boolean
    ) {

        _marketData.value = when (sortType) {

            // 거래량
            SortType.AMOUNT -> {
                if (!state)
                    list.sortedByDescending { it.accTradePrice24H?.toDoubleOrNull() ?: 0.0 }
                else
                    list.sortedBy { it.accTradePrice24H?.toDoubleOrNull() ?: 0.0 }
            }
            // 전일 대비
            SortType.COMPARE_PREVIOUS_DAY -> {
                if (!state)
                    list.sortedByDescending { it.signedChangeRate?.toDoubleOrNull() ?: 0.0 }
                else
                    list.sortedBy { it.signedChangeRate?.toDoubleOrNull() ?: 0.0 }
            }

            // 현재가
            SortType.CURRENT_PRICE -> {
                if (!state)
                    list.sortedByDescending { it.tradePrice?.toDoubleOrNull() ?: 0.0 }
                else
                    list.sortedBy { it.tradePrice?.toDoubleOrNull() ?: 0.0 }

            }

//        _marketData.value = list.sortedByDescending {
//
//            it.accTradePrice24H?.toDoubleOrNull() ?: 0.0
//
//        }

        }
    }

    private fun getCurrentSortState(): Boolean {

        return when (currentSortType) {
            SortType.AMOUNT -> transactionAmountState
            SortType.COMPARE_PREVIOUS_DAY -> comparePreviousDayState
            SortType.CURRENT_PRICE -> currentPriceState
        }

    }

    fun setSortType(sortType: SortType) {

        currentSortType = sortType
        updateMarketDataState(_marketData.value ?: emptyList(), sortType, getCurrentSortState())

    }


}




