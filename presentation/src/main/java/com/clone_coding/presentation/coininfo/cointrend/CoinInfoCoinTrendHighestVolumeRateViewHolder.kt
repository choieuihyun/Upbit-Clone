package com.clone_coding.presentation.coininfo.cointrend

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinInfoCoinTrendVolumePowerRateModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoCointrendVolumepowerRateListItemBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

class CoinInfoCoinTrendHighestVolumeRateViewHolder @Inject constructor(
    private val binding: FragmentCoinInfoCointrendVolumepowerRateListItemBinding,
    private val coinTickerMap: Map<String, String>
) :RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CoinInfoCoinTrendVolumePowerRateModel) {

        binding.volumePowerRateModel = data

        val code = data.market
        binding.listKoreanName.text = coinTickerMap[code] ?: "Unknown"

        val volumePowerRate = (data.accBidVolume.toLong() / data.accAskVolume.toLong()) * 100
        Log.d("volumeViewHolderAccAskVolume", code + " " + data.accAskVolume.toString())
        Log.d("volumeViewHolderAccBidVolume", code + " " + data.accBidVolume.toString())
        Log.d("volumeViewHolderVolumePowerRate", "$code $volumePowerRate")
        //val formattedIncreaseRate = DecimalFormat("#.##").format(volumePowerRate).toString() + "%"

        binding.volumePowerRate.text = volumePowerRate.toString()




    }

}