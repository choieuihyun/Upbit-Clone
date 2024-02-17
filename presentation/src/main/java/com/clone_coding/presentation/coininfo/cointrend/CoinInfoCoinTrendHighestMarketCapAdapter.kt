package com.clone_coding.presentation.coininfo.cointrend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.clone_coding.domain.model.MarketCapListModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoCointrendMarketcapListItemBinding

class CoinInfoCoinTrendHighestMarketCapAdapter: ListAdapter<MarketCapListModel, CoinInfoCoinTrendHighestMarketCapViewHolder>(
    diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinInfoCoinTrendHighestMarketCapViewHolder {

        return CoinInfoCoinTrendHighestMarketCapViewHolder(
            FragmentCoinInfoCointrendMarketcapListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(
        holder: CoinInfoCoinTrendHighestMarketCapViewHolder,
        position: Int
    ) {

        val highestMarketCap = currentList[position]
        holder.bind(highestMarketCap)

    }

    companion object {

        private val diffCallback =
            object : DiffUtil.ItemCallback<MarketCapListModel>() {

                override fun areItemsTheSame(
                    oldItem: MarketCapListModel,
                    newItem: MarketCapListModel
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(
                    oldItem: MarketCapListModel,
                    newItem: MarketCapListModel
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }


}