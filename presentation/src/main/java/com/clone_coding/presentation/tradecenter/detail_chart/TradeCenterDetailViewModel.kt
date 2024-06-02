package com.clone_coding.presentation.tradecenter.detail_chart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TradeCenterDetailChartModel
import com.clone_coding.domain.repository.UpbitTradeCenterDetailChartRepository
import com.clone_coding.presentation.tradecenter.TradeCenterKRWTabViewModel
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeCenterDetailViewModel @Inject constructor(
    private val repository: UpbitTradeCenterDetailChartRepository
) : ViewModel() {

    data class Candle(
        // 원래는 Long이나 Float이다.
        val createdAt: Float, // 캔들의 x좌표, 근데 날짜로 한거면 String으로 해야함 이거 해본거잖아. 경험이 있다.
        val open: Float, // 시가
        val close: Float, // 종가
        val shadowHigh: Float, // 장중 최고가
        val shadowLow: Float // 장중 최저가
    )

    enum class Period {

        MINUTE, DAY, WEEK, MONTH

    }

    var currentSortType = Period.DAY // 초기 데이터는 거래대금 내림차순

    private val _chartCandleEntryList = MutableLiveData<List<CandleEntry>?>()
    val chartCandleEntryList: LiveData<List<CandleEntry>?>
        get() = _chartCandleEntryList

    private val _chartCandleList = MutableLiveData<List<Candle>?>()
    val chartCandleList: LiveData<List<Candle>?>
        get() = _chartCandleList

    /*    fun getTradeCenterChartInformation(
            market: String, to: String, count: Int, convertingPriceUnit: String
        ) {

            viewModelScope.launch {

                when (val result = repository.getTradeCenterChartInformation(
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

                    }

                    is NetworkResult.Error -> {

                        Log.e("networkError", result.errorType.toString())

                    }

                }

            }

        }*/

    private fun updateTradeCenterChartInformationByPeriod(
        period: Period,
        market: String,
        to: String,
        count: Int,
        convertingPriceUnit: String // 일단 냅둬야함.
    ) {

        viewModelScope.launch {

            when (period) {

                Period.MINUTE -> {

                }

                // 일봉만 convertingPriceUnit라는 데이터가 필요함.
                Period.DAY -> {

                    val result = repository.getTradeCenterChartInformation(
                        market,
                        to,
                        count,
                        "KRW"
                    )

                    if (result is NetworkResult.Success)
                        candleProcess(result.data)
                    else if (result is NetworkResult.Error)
                        Log.e("networkError", result.errorType.toString())

                }

                Period.WEEK -> {
                    val result = repository.getTradeCenterWeekChartInformation(
                        market,
                        to,
                        count
                    )

                    if (result is NetworkResult.Success)
                        candleProcess(result.data)
                    else if (result is NetworkResult.Error)
                        Log.e("networkError", result.errorType.toString())
                }

                Period.MONTH -> {}

            }
        }

    }

    /**
     * 일봉기준 데이터 불러오는 메서드
     * @param market = KRW-BTC와 같은 코인 코드
     * @param to = 마지막 캔들 시각
     * @param count = 캔들 갯수
     * @param convertingPriceUnit = 이건 일봉에만 있는 환산 화폐 단위
     */
    fun getTradeCenterChartInformation(
        market: String, to: String, count: Int, convertingPriceUnit: String
    ) {

        viewModelScope.launch {
            when (val result = repository.getTradeCenterChartInformation(
                market, to, count, convertingPriceUnit
            )) {
                is NetworkResult.Success -> {
                    candleProcess(result.data)
                }

                is NetworkResult.Error -> {
                    Log.e("networkError", result.errorType.toString())
                }
            }
        }

    }

//    fun getTradeCenterChartInformationByPeriod(
//        market: String, to: String, count: Int
//    ) {
//
//        viewModelScope.launch {
//            when (val result = repository.getTradeCenterWeekChartInformation(
//                market, to, count
//            )) {
//                is NetworkResult.Success -> {
//                    candleProcess(result.data)
//                }
//
//                is NetworkResult.Error -> {
//                    Log.e("networkError", result.errorType.toString())
//                }
//            }
//        }
//
//    }


    private fun candleProcess(data: List<TradeCenterDetailChartModel>?) {

        // 어차피 코인이 있는 이상 차트 데이터는 무조건 존재한다. 이것으로 !!의 설명이 될까?
        data!!.let {

            // 이렇게 하지 말고 어차피 날짜 기준 바꿀때마다 새로 데이터 불러오는건 오래 걸릴거 같은데?
            // 어차피 뷰모델 사용하니까 minute, day, week, month 리스트 따로 만들어놓고 클릭때마다 그 데이터만 관찰해서 띄우는게 정배아님?
            val candles = createCandles(it)
            val candlesEntry = createCandlesEntry(candles)
            _chartCandleList.value = candles
            _chartCandleEntryList.value = candlesEntry

        }

    }

    /**
     * 차트 캔들 리스트 데이터 삽입 메서드
     * @param : 차트에 필요한 모델 데이터 클래스
     */
    private fun createCandles(data: List<TradeCenterDetailChartModel>): MutableList<Candle> {

        val candles = mutableListOf<Candle>()

        for (index in data.indices.reversed()) {

            candles.add(

                Candle(

                    data.size - 1 - index.toFloat(), // 사실 이 부분은 필요없겠지?
                    data[index].openingPrice.toFloat(), // 시가
                    data[index].tradePrice.toFloat(), // 종가
                    data[index].highPrice.toFloat(), // 고가
                    data[index].lowPrice.toFloat() // 저가

                )

            )

        }

        return candles

    }

    /**
     * 차트 캔들 엔트리 리스트 데이터 삽입 메서드
     * @param : 차트에 필요한 캔들 리스트
     */
    private fun createCandlesEntry(candles: List<Candle>): MutableList<CandleEntry> {

        val candlesEntry = mutableListOf<CandleEntry>()

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

        return candlesEntry

    }

    fun setSortType(sortType: Period, market: String, to: String, count: Int) {

        currentSortType = sortType
        updateTradeCenterChartInformationByPeriod(currentSortType, market, to, count, "")

    }


}