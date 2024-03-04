package com.clone_coding.presentation.withdraw

import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinWithdrawCoinListModel
import com.clone_coding.domain.model.CoinWithdrawModel
import com.clone_coding.presentation.databinding.FragmentWithdrawListItemBinding

class WithdrawViewHolder(

    private val binding: FragmentWithdrawListItemBinding

): RecyclerView.ViewHolder(binding.root) {


    fun bind(data: CoinWithdrawCoinListModel) {

        binding.withdrawModel = data

    }

}