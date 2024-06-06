package com.clone_coding.presentation.tradecenter.detail_chart

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentTradeDetailChartBinding
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeCenterDetailChartFragment: BaseFragment<FragmentTradeDetailChartBinding>(R.layout.fragment_trade_detail_chart) {

    private lateinit var combinedChart: CombinedChart
    private val viewModel: TradeCenterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getSerializable("chartModel") as TradeCenterKRWTabModel

        combinedChart = binding.priceChart

        viewModel.getTradeCenterChartInformation(args.code!!,"", 50, "KRW")

        viewModel.chartCandleList.observe(viewLifecycleOwner) { candleList ->

            viewModel.chartCandleEntryList.observe(viewLifecycleOwner) { candleEntryList ->

                setChartData(candleEntryList, candleList)

            }

        }

        initChart()

        val originalTextColor = binding.day.textColors


        // 이걸 굳이 메서드화 시켜야하나? argument만 파라미터로 추가될 뿐인데.
        binding.minute.setOnClickListener {
            onClickListener(it, originalTextColor)
            viewModel.setSortType(TradeCenterDetailViewModel.Period.MINUTE, args.code!!, "", 50)
        }
        binding.day.setOnClickListener {
            onClickListener(it, originalTextColor)
            viewModel.setSortType(TradeCenterDetailViewModel.Period.DAY, args.code!!, "", 50)
        }
        binding.week.setOnClickListener {
            onClickListener(it, originalTextColor)
            viewModel.setSortType(TradeCenterDetailViewModel.Period.WEEK, args.code!!, "", 30)
        }
        binding.month.setOnClickListener {
            onClickListener(it, originalTextColor)
            viewModel.setSortType(TradeCenterDetailViewModel.Period.MONTH, args.code!!, "", 15)
        }


        //setChartData(candles)

    }

    // 여기에 더 많은 지표를 추가할 수 있습니다.
    enum class MainIndicator {
        MOVING_AVERAGE_ONE, // 여기서 초, 분, 시, 일, 주, 월 봉 설정하는 enum 클래스.
        MOVING_AVERAGE_TWO
    }

    // candle Data를 어떻게 할꺼냐면, createdAt은 float으로 해서 순차적으로 추가할꺼야.
    fun initChart() {

        binding.apply {

            priceChart.description.isEnabled = false
            priceChart.setMaxVisibleValueCount(200)
            priceChart.setPinchZoom(false)
            priceChart.setDrawGridBackground(false)

            // x축 설정
            priceChart.xAxis.apply {

                textColor = Color.TRANSPARENT
                position = XAxis.XAxisPosition.BOTTOM
                // 세로선 표시 여부 설정
                this.setDrawGridLines(true)
                axisLineColor = Color.rgb(50, 59, 76)
                gridColor = Color.rgb(50, 59, 76)
                // valueFormatter = MyValueFormatter()

            }

            // 왼쪽 y축 설정
            priceChart.axisLeft.apply {
                textColor = Color.WHITE
                isEnabled = false
            }

            // 오른쪽 y축 설정
            priceChart.axisRight.apply {
                setLabelCount(7, false)
                textColor = Color.WHITE
                // 가로선 표시 여부 설정
                setDrawGridLines(true)
                // 차트의 오른쪽 테두리 라인 설정
                setDrawAxisLine(true)
                axisLineColor = Color.rgb(50, 59, 76)
                gridColor = Color.rgb(50, 59, 76)
            }
            setChartLegend(0) // enum 따라가용
        }
    }

    fun setChartLegend(mainIndicatorType: Int) {
        val legendList: List<LegendEntry> = when (mainIndicatorType) {
            MainIndicator.MOVING_AVERAGE_ONE.ordinal -> {
                binding.priceChart.legend.isEnabled = true
                val movingAverageLegend = LegendEntry()
                movingAverageLegend.label = "단순 MA"
                // form을 사용하지 않는 legend의 경우에는
                // form 값을 NONE으로 설정하면 된다.
                movingAverageLegend.form = Legend.LegendForm.NONE
                val averageN1Legend = LegendEntry()
                averageN1Legend.label = "5"
                averageN1Legend.formColor = Color.rgb(219, 17, 179)
                val averageN2Legend = LegendEntry()
                averageN2Legend.label = "10"
                averageN2Legend.formColor = Color.rgb(11, 41, 175)
                val averageN3Legend = LegendEntry()
                averageN3Legend.label = "20"
                averageN3Legend.formColor = Color.rgb(234, 153, 1)
                val averageN4Legend = LegendEntry()
                averageN4Legend.label = "60"
                averageN4Legend.formColor = Color.rgb(253, 52, 0)
                val averageN5Legend = LegendEntry()
                averageN5Legend.label = "120"
                averageN5Legend.formColor = Color.rgb(170, 170, 170)
                listOf(
                    movingAverageLegend,
                    averageN1Legend,
                    averageN2Legend,
                    averageN3Legend,
                    averageN4Legend,
                    averageN5Legend
                )
            } else -> {
                binding.priceChart.legend.isEnabled = true
                val movingAverageLegend = LegendEntry()
                movingAverageLegend.label = "단순 MA"
                // form을 사용하지 않는 legend의 경우에는
                // form 값을 NONE으로 설정하면 된다.
                movingAverageLegend.form = Legend.LegendForm.NONE
                val averageN1Legend = LegendEntry()
                averageN1Legend.label = "5"
                averageN1Legend.formColor = Color.rgb(219, 17, 179)
                val averageN2Legend = LegendEntry()
                averageN2Legend.label = "10"
                averageN2Legend.formColor = Color.rgb(11, 41, 175)
                val averageN3Legend = LegendEntry()
                averageN3Legend.label = "20"
                averageN3Legend.formColor = Color.rgb(234, 153, 1)
                val averageN4Legend = LegendEntry()
                averageN4Legend.label = "60"
                averageN4Legend.formColor = Color.rgb(253, 52, 0)
                val averageN5Legend = LegendEntry()
                averageN5Legend.label = "120"
                averageN5Legend.formColor = Color.rgb(170, 170, 170)
                listOf(
                    movingAverageLegend,
                    averageN1Legend,
                    averageN2Legend,
                    averageN3Legend,
                    averageN4Legend,
                    averageN5Legend
                )
            }
        }
        binding.priceChart.legend.apply {
            // legend 데이터 설정
            setCustom(legendList)
            // legend 텍스트 컬러 설정
            textColor = Color.WHITE
            // legend의 위치를 좌측 상단으로 설정함
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            // 수평 방향으로 정렬함
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(true)
        }
    }

    fun setChartData(candleEntries: List<CandleEntry>?, candles: List<TradeCenterDetailViewModel.Candle>?) {
//        val priceEntries = ArrayList<CandleEntry>()
//        for (candle in candles) {
//            // 캔들 차트 entry 생성
//            priceEntries.add(
//                CandleEntry(
//                    candle.createdAt.toFloat(),
//                    candle.shadowHigh,
//                    candle.shadowLow,
//                    candle.open,
//                    candle.close
//                )
//            )
//        }

        Log.d("ssssssCandle", candleEntries.toString())

        val priceDataSet = CandleDataSet(candleEntries, "").apply {
            axisDependency = YAxis.AxisDependency.LEFT

            // 심지 부분 설정
            shadowColor = Color.LTGRAY
            shadowWidth = 0.7F

            // 양봉 심지는 빨간색, 음봉 심지는 파란색 시도하다가 일단은 보류. 포기는 없다.
//            for (index in candles!!.indices) {
//
//                val candle = candles[index]
//
//                if(candle.close >= candle.open) {
//                    shadowColor = Color.RED
//                    shadowWidth = 0.7F
//                } else {
//                    shadowColor = Color.BLUE
//                    shadowWidth = 0.7F
//                }
//
//            }


            // 음봉 설정
            decreasingColor = Color.rgb(20, 60, 240)
            decreasingPaintStyle = Paint.Style.FILL
            // 양봉 설정
            increasingColor = Color.rgb(240, 60, 20)
            increasingPaintStyle = Paint.Style.FILL

            neutralColor = Color.rgb(6, 18, 34)
            setDrawValues(false)
            // 터치시 노란 선 제거
            highLightColor = Color.TRANSPARENT
        }

        binding.priceChart.apply {
            // CombinedData 만들어주고
            val combinedData = CombinedData()
            // CombinedData에 CandleData를 추가해주고
            combinedData.setData(CandleData(priceDataSet))
            // LineData 만들어주고
            val lineData = getMovingAverage(candles)
            // CombinedData에 LineData를 추가해주고
            combinedData.setData(lineData)
            // CombinedChart에 CombinedData를 적용시켜주면 된다.
            this.data = combinedData

            invalidate()
        }
    }

    // 이동평균선 데이터를 갖는 LineData를 리턴하는 함수
    fun getMovingAverage(candles: List<TradeCenterDetailViewModel.Candle>?): LineData {
        // 마지막에 리턴할 LineData 만들어주고
        val ret: LineData = LineData()

        val N1: Int = 5
        val N2: Int = 10
        val N3: Int = 20
        val N4: Int = 60
        val N5: Int = 120
        // LineChart에 추가할 데이터 == ArrayList<Entry> 생성해주고
        val averageN1Entries = ArrayList<Entry>()
        val averageN2Entries = ArrayList<Entry>()
        val averageN3Entries = ArrayList<Entry>()
        val averageN4Entries = ArrayList<Entry>()
        val averageN5Entries = ArrayList<Entry>()
        var count: Int = 0
        var sumN1: Float = 0.0f
        var sumN2: Float = 0.0f
        var sumN3: Float = 0.0f
        var sumN4: Float = 0.0f
        var sumN5: Float = 0.0f
        // 이동평균선 데이터를 계산하는 for loop
        // 데이터 생성은 Entry(createdAt, price) 로 하면 된다.
        for (candle in candles!!) {
            count++
            sumN1 += candle.close
            sumN2 += candle.close
            sumN3 += candle.close
            sumN4 += candle.close
            sumN5 += candle.close
            val now = candles.indexOf(candle)
            if (count >= N5) {
                averageN5Entries.add(
                    Entry(
                        candle.createdAt.toFloat(),
                        sumN5 / N5.toFloat()
                    )
                )
                sumN5 -= candles[now - (N5 - 1)].close
            }
            if (count >= N4) {
                averageN4Entries.add(
                    Entry(
                        candle.createdAt.toFloat(),
                        sumN4 / N4.toFloat()
                    )
                )
                sumN4 -= candles[now - (N4 - 1)].close
            }
            if (count >= N3) {
                averageN3Entries.add(
                    Entry(
                        candle.createdAt.toFloat(),
                        sumN3 / N3.toFloat()
                    )
                )
                sumN3 -= candles[now - (N3 - 1)].close
            }
            if (count >= N2) {
                averageN2Entries.add(
                    Entry(
                        candle.createdAt.toFloat(),
                        sumN2 / N2.toFloat()
                    )
                )
                sumN2 -= candles[now - (N2 - 1)].close
            }
            if (count >= N1) {
                averageN1Entries.add(Entry(candle.createdAt.toFloat(), sumN1 / N1.toFloat()))
                sumN1 -= candles[now - (N1 - 1)].close
            }
        }

        // for loop에서 만든 Entry들로 LineDataSet을 만들어준다.
        val averageN1DataSet = LineDataSet(averageN1Entries, "").apply {
            setDrawCircles(false)
            color = Color.rgb(219, 17, 179)
            highLightColor = Color.TRANSPARENT
            valueTextSize = 0f
            lineWidth = 1.0f
        }
        val averageN2DataSet = LineDataSet(averageN2Entries, "").apply {
            setDrawCircles(false)
            color = Color.rgb(11, 41, 175)
            highLightColor = Color.TRANSPARENT
            valueTextSize = 0f
            lineWidth = 1.0f
        }
        val averageN3DataSet = LineDataSet(averageN3Entries, "").apply {
            setDrawCircles(false)
            color = Color.rgb(234, 153, 1)
            highLightColor = Color.TRANSPARENT
            valueTextSize = 0f
            lineWidth = 1.0f
        }
        val averageN4DataSet = LineDataSet(averageN4Entries, "").apply {
            setDrawCircles(false)
            color = Color.rgb(253, 52, 0)
            highLightColor = Color.TRANSPARENT
            valueTextSize = 0f
            lineWidth = 1.0f
        }
        val averageN5DataSet = LineDataSet(averageN5Entries, "").apply {
            setDrawCircles(false)
            color = Color.rgb(170, 170, 170)
            highLightColor = Color.TRANSPARENT
            valueTextSize = 0f
            lineWidth = 1.0f
        }
        // LineData에 LineDataSet을 추가해준다.
        ret.addDataSet(averageN1DataSet)
        ret.addDataSet(averageN2DataSet)
        ret.addDataSet(averageN3DataSet)
        ret.addDataSet(averageN4DataSet)
        ret.addDataSet(averageN5DataSet)

        return ret
    }

    class MyValueFormatter(private val xValues: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val index = value.toInt()
            return if (index >= 0 && index < xValues.size) {
                xValues[index]
            } else {
                ""
            }
        }
    }


    private fun onClickListener(clickedText: View, originalColor: ColorStateList) {
        val clickedId = clickedText.id

        // 현재 클릭된 텍스트의 클릭 상태에 따라 처리
        when (clickedId) {
            binding.minute.id -> {
                if (!binding.minute.isSelected) {

                    // 현재 클릭된 텍스트의 클릭 상태 변경
                    binding.minute.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    // 메서드화 필요.
                    setupBorder(clickedText, R.color.blue)

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.day.isSelected = false
                    binding.day.setTextColor(originalColor)
                    setupBorder(binding.day, R.color.white)

                    binding.week.isSelected = false
                    binding.week.setTextColor(originalColor)
                    setupBorder(binding.week, R.color.white)

                    binding.month.isSelected = false
                    binding.month.setTextColor(originalColor)
                    setupBorder(binding.month, R.color.white)


                } else {

                    binding.minute.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)
                    setupBorder(clickedText, R.color.white)

                }

            }

            binding.day.id -> {
                if (!binding.day.isSelected) {

                    binding.day.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    setupBorder(clickedText, R.color.blue)

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.minute.isSelected = false
                    binding.minute.setTextColor(originalColor)
                    setupBorder(binding.minute, R.color.white)

                    binding.week.isSelected = false
                    binding.week.setTextColor(originalColor)
                    setupBorder(binding.week, R.color.white)

                    binding.month.isSelected = false
                    binding.month.setTextColor(originalColor)
                    setupBorder(binding.month, R.color.white)
                } else {

                    binding.day.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)
                    setupBorder(clickedText, R.color.white)

                }

            }

            binding.week.id -> {
                if (!binding.week.isSelected) {

                    binding.week.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    setupBorder(clickedText, R.color.blue)

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.minute.isSelected = false
                    binding.minute.setTextColor(originalColor)
                    setupBorder(binding.minute, R.color.white)

                    binding.day.isSelected = false
                    binding.day.setTextColor(originalColor)
                    setupBorder(binding.day, R.color.white)

                    binding.month.isSelected = false
                    binding.month.setTextColor(originalColor)
                    setupBorder(binding.month, R.color.white)
                } else {

                    binding.week.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)
                    setupBorder(clickedText, R.color.white)

                }

            }

            binding.month.id -> {
                if (!binding.month.isSelected) {

                    binding.month.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    setupBorder(clickedText, R.color.blue)

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.minute.isSelected = false
                    binding.minute.setTextColor(originalColor)
                    setupBorder(binding.minute, R.color.white)

                    binding.day.isSelected = false
                    binding.day.setTextColor(originalColor)
                    setupBorder(binding.day, R.color.white)

                    binding.week.isSelected = false
                    binding.week.setTextColor(originalColor)
                    setupBorder(binding.week, R.color.white)
                } else {

                    binding.month.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)
                    setupBorder(clickedText, R.color.white)

                }


            }
        }
    }

    private fun setupBorder(clickedText: View, color: Int) {

        var drawable = ContextCompat.getDrawable(requireContext(), R.drawable.trade_center_detail_timeunits_border)
        if (drawable is GradientDrawable) {
            drawable.setStroke(1, ContextCompat.getColor(requireContext(), color)) // 파란색으로 변경
            clickedText.setBackgroundDrawable(drawable)
        }

    }

}

