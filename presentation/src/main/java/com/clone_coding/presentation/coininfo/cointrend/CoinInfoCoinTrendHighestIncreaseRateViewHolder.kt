package com.clone_coding.presentation.coininfo.cointrend

import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinInfoCoinTrendHighestIncreaseRateModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoCointrendHighestIncreaseRateListItemBinding
import java.text.DecimalFormat

class CoinInfoCoinTrendHighestIncreaseRateViewHolder(
    private val binding: FragmentCoinInfoCointrendHighestIncreaseRateListItemBinding,
    private val coinTickerMap: Map<String, String>
): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CoinInfoCoinTrendHighestIncreaseRateModel) {

        binding.increaseRateModel = data

        val code = data.market
        binding.listKoreanName.text = coinTickerMap[code] ?: "Unknown"

        val increaseRate = (((data.tradePrice - data.openingPrice) / data.openingPrice) * 100)
        val formattedIncreaseRate = DecimalFormat("#.##").format(increaseRate).toString() + "%"
        binding.increaseRate.text = formattedIncreaseRate



    }

}