package com.clone_coding.presentation.coininfo.cointrend

import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.MarketCapListModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoCointrendMarketcapListItemBinding

class CoinInfoCoinTrendHighestMarketCapViewHolder (
    private val binding: FragmentCoinInfoCointrendMarketcapListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: MarketCapListModel) {

        binding.marketCapModel = data

    }

}