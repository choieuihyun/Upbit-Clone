package com.clone_coding.presentation.coininfo.coinnews

import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinInfoCoinNewsLatestNewsModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoCoinnewsLatestnewsListItemBinding

class CoinInfoCoinNewsLatestNewsViewHolder(
    private val binding: FragmentCoinInfoCoinnewsLatestnewsListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CoinInfoCoinNewsLatestNewsModel) {

        binding.latestNewsModel = data

        // binding.executePendingBindings()
    }

}