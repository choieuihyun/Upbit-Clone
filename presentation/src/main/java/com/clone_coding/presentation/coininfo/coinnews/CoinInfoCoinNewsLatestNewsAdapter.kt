package com.clone_coding.presentation.coininfo.coinnews

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.clone_coding.domain.model.CoinInfoCoinNewsLatestNewsModel
import com.clone_coding.domain.model.CoinInfoCoinTrendHighestIncreaseRateModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoCoinnewsLatestnewsListItemBinding

class CoinInfoCoinNewsLatestNewsAdapter: ListAdapter<CoinInfoCoinNewsLatestNewsModel, CoinInfoCoinNewsLatestNewsViewHolder>(
    diffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinInfoCoinNewsLatestNewsViewHolder {
        return CoinInfoCoinNewsLatestNewsViewHolder(
            FragmentCoinInfoCoinnewsLatestnewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: CoinInfoCoinNewsLatestNewsViewHolder, position: Int) {

        val latestNewsModel = currentList[position]

        holder.bind(latestNewsModel)

    }



    companion object {

        private val diffCallback =
            object : DiffUtil.ItemCallback<CoinInfoCoinNewsLatestNewsModel>() {

                override fun areItemsTheSame(
                    oldItem: CoinInfoCoinNewsLatestNewsModel,
                    newItem: CoinInfoCoinNewsLatestNewsModel
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(
                    oldItem: CoinInfoCoinNewsLatestNewsModel,
                    newItem: CoinInfoCoinNewsLatestNewsModel
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }


}