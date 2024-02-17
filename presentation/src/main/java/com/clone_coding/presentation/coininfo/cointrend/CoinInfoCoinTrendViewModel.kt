package com.clone_coding.presentation.coininfo.cointrend

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.MarketCapListModel
import com.clone_coding.domain.model.CoinInfoCoinTrendHighestIncreaseRateModel
import com.clone_coding.domain.model.CoinInfoCoinTrendVolumePowerRateModel
import com.clone_coding.domain.repository.CoinInfoCoinTrendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinInfoCoinTrendViewModel @Inject constructor(
    private val repository: CoinInfoCoinTrendRepository
) : ViewModel() {

    private val _highestIncreaseFiveRate =
        MutableLiveData<List<CoinInfoCoinTrendHighestIncreaseRateModel>>()
    val highestIncreaseFiveRate
        get() = _highestIncreaseFiveRate

    // 마켓 코드 (ex. KRW-BTC)
    private val _market = repository.market

    // 누적 매도량
    private val _accAskVolume = repository.accAskVolume

    // 누적 매수량
    private val _accBidVolume = repository.accBidVolume

    private val _volumePowerRate = MediatorLiveData<Pair<String?, String?>>()


    // 매수/매도 체결량 리스트
    private val _volumePowerRateList =
        MutableLiveData<List<CoinInfoCoinTrendVolumePowerRateModel>>()
    val volumePowerRateList: LiveData<List<CoinInfoCoinTrendVolumePowerRateModel>>
        get() = _volumePowerRateList

    private val _marketCapHighestList = MutableLiveData<List<MarketCapListModel>>()
    val marketCapHighestList: LiveData<List<MarketCapListModel>>
        get() = _marketCapHighestList

    fun getCoinInfoCoinTrendHighestRate() {

        // 초기 데이터
        val highestIncreaseRateInitialList = mutableListOf<List<CoinInfoCoinTrendHighestIncreaseRateModel>?>()
        // 뷰에 띄울 데이터(상승률 상위 코인)
        val highestIncreaseRateFive = mutableListOf<CoinInfoCoinTrendHighestIncreaseRateModel>()

        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                val highestIncreaseRateList = repository.getCoinInfoCoinTrendHighestIncreaseRate()
                repository.getCoinInfoCoinTrendRealTimeInfo()
                // repository.getCoinInfoCoinTrendVolumePower()

                when (highestIncreaseRateList) {
                    is NetworkResult.Success -> {
                        for (i in highestIncreaseRateList.data.indices) {

                            highestIncreaseRateInitialList.add(highestIncreaseRateList.data[i])

                        }

                    }

                    is NetworkResult.Error -> Log.d("coinViewModelError", "asd")
                }

                for (list in highestIncreaseRateInitialList) {

                    list?.let { innerList ->

                        for (item in innerList) {
                            highestIncreaseRateFive.add(item)
                        }

                    }

                }

                val sortedHighestFive = highestIncreaseRateFive.sortedByDescending {
                    ((it.tradePrice - it.openingPrice) / it.openingPrice) * 100
                }.take(5)

                Log.d("tradePriceHighest", sortedHighestFive.toString())

                _highestIncreaseFiveRate.postValue(sortedHighestFive)

            }


            // 코인 시가 총액
            val marketCapList = mutableListOf<List<MarketCapListModel>?>()

            withContext(Dispatchers.IO) {

                val coinMarketCapResponseList = repository.getCoinMarketCapList()

                when(coinMarketCapResponseList) {
                    is NetworkResult.Success -> {

                        Log.d("marketCapList", coinMarketCapResponseList.data.toString())

                        for(data in coinMarketCapResponseList.data) {

                            marketCapList.add(data)

                        }

                    }
                    is NetworkResult.Error -> {
                        Log.d("coinViewModelError", "asdasd")
                    }
                }

                val highestMarketCapFive = mutableListOf<MarketCapListModel>()

                for(list in marketCapList) {

                    list?.let { innerList ->

                        for(item in innerList) {

                            highestMarketCapFive.add(item)
                        }

                    }

                }

                val sortedHighestMarketCapFive = highestMarketCapFive.sortedByDescending { it.marketCap }.take(5)

                Log.d("marketCapHighest", sortedHighestMarketCapFive.toString())

                _marketCapHighestList.postValue(sortedHighestMarketCapFive)
            }



            // 여기서부터 체결량

//            _volumePowerRate.addSource(_accAskVolume) { askVolume ->
//
//                val bidVolume = _accBidVolume.value
//
//                if (askVolume != null && bidVolume != null) // 첫번째 데이터가 계속 null이 관찰됨 ㅠㅠ
//                    _volumePowerRate.value = Pair(askVolume, bidVolume)
//            }
//
//            _volumePowerRate.addSource(_accBidVolume) { bidVolume ->
//
//                val askVolume = _accAskVolume.value
//
//                if (askVolume != null && bidVolume != null)
//                    _volumePowerRate.value = Pair(askVolume, bidVolume)
//            }

            Log.d("sdfsdfsdf", _volumePowerRate.value?.first.toString())
            _volumePowerRate.observeForever { (askVolume, bidVolume) ->

                Log.d("coinViewModel매수체결량", _market.value + " " + bidVolume.toString())
                Log.d("coinViewModel매도체결량", _market.value + " " + askVolume.toString())

                val currentMarketVolume = _volumePowerRateList.value ?: emptyList()

                val existingItemIndex =
                    currentMarketVolume.indexOfFirst { it.market == _market.value && it.accAskVolume != _accAskVolume.value}

                val updatedMarketVolumeList = if (existingItemIndex != -1) {
                    val updatedMarketVolume = currentMarketVolume.toMutableList()
                    updatedMarketVolume[existingItemIndex] = CoinInfoCoinTrendVolumePowerRateModel(
                        currentMarketVolume[existingItemIndex].market ?: "",
                        currentMarketVolume[existingItemIndex].accAskVolume ?: "",
                        currentMarketVolume[existingItemIndex].accBidVolume ?: ""
                    )
                    updatedMarketVolume
                } else {
                    currentMarketVolume + CoinInfoCoinTrendVolumePowerRateModel(
                        _market.value ?: "",
                        _accAskVolume.value ?: "",
                        _accBidVolume.value ?: ""
                    )
                }

                _volumePowerRateList.value = updatedMarketVolumeList
                    .sortedByDescending { (it.accBidVolume.toLong() / it.accAskVolume.toLong()) * 100 }
                    .take(5)

            }

        }
    }


}
