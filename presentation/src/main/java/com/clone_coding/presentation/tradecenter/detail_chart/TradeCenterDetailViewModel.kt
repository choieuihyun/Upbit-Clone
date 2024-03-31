package com.clone_coding.presentation.tradecenter.detail_chart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.repository.UpbitTradeCenterDetailChartRepository
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeCenterDetailViewModel @Inject constructor(
    private val repository: UpbitTradeCenterDetailChartRepository
): ViewModel() {

    data class Candle(
        // 원래는 Long이나 Float이다.
        val createdAt: Float, // 캔들의 x좌표, 근데 날짜로 한거면 String으로 해야함 이거 해본거잖아. 경험이 있다.
        val open: Float, // 시가
        val close: Float, // 종가
        val shadowHigh: Float, // 장중 최고가
        val shadowLow: Float // 장중 최저가
    )

    private val _chartCandleEntryList = MutableLiveData<List<CandleEntry>?>()
    val chartCandleEntryList: LiveData<List<CandleEntry>?>
        get() = _chartCandleEntryList

    private val _chartCandleList = MutableLiveData<List<Candle>?>()
    val chartCandleList: LiveData<List<Candle>?>
        get() = _chartCandleList

    fun getTradeCenterChartInformation(
        market: String,
        to: String,
        count: Int,
        convertingPriceUnit: String
    ) {

        viewModelScope.launch {

            when(val result = repository.getTradeCenterChartInformation(
                market, to, count, convertingPriceUnit
            )) {

                // 저가를 shadowLow에 하고 이런 방식으로 해야되네
                // 애초에 데이터가 잘못되었기 때문에 차트가 제대로 안뜨지.
                is NetworkResult.Success -> {

                    val candles = mutableListOf<Candle>()
                    val candlesEntry = mutableListOf<CandleEntry>()

                    for (index in result.data!!.indices.reversed()) {

                        candles.add(

                            Candle(

                                (result.data!!.size - 1 - index).toFloat(), // 사실 이 부분은 필요없겠지?
                                result.data!![index].openingPrice.toFloat(), // 시가
                                result.data!![index].tradePrice.toFloat(), // 종가
                                result.data!![index].highPrice.toFloat(), // 고가
                                result.data!![index].lowPrice.toFloat() // 저가

                            )

                        )

                    }

                    Log.d("ssssssViewModelCandles", candles.toString())

                    for (index in candles.indices) {

                        // 이게 임의 데이터로 해보니까 shadowHigh, shadowLow의 중간 값으로 차트가 만들어짐
                        // Entry 생성 과정을 보니까 앞의 두개를 기준으로 값을 계산하더라
                        // 그래서 그렇게 하기 위해서 Entry를 저렇게 만들었는데 뭐지 위에서 reversed가 문제인가
                        candlesEntry.add(
                            CandleEntry(
                                index.toFloat(), // 이 부분이 날짜일텐데
                                candles[index].shadowHigh, // 시가
                                candles[index].shadowLow, // 종가
                                candles[index].open, // 이게 위에 작대기
                                candles[index].close // 이게 아래 작대기
                            )
                        )

                    }

                    _chartCandleEntryList.value = candlesEntry
                    _chartCandleList.value = candles
                    Log.d("ssssssViewModelEntry", candlesEntry.toString())

                }

                is NetworkResult.Error -> {

                    Log.e("networkError", result.errorType.toString())

                }

            }

        }

    }

}