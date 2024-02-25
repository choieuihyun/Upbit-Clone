package com.clone_coding.presentation.withdraw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel
import com.clone_coding.domain.model.CoinWithdrawCoinListModel
import com.clone_coding.domain.model.CoinWithdrawModel
import com.clone_coding.presentation.databinding.FragmentWithdrawListItemBinding

class WithdrawRecyclerViewAdapter: ListAdapter<CoinWithdrawCoinListModel, WithdrawViewHolder>(
    diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithdrawViewHolder {

        return WithdrawViewHolder(
            FragmentWithdrawListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: WithdrawViewHolder, position: Int) {

        val withdraw = currentList[position]

        holder.bind(withdraw)

    }


    companion object {

        private val diffCallback =
            object : DiffUtil.ItemCallback<CoinWithdrawCoinListModel>() {

                override fun areItemsTheSame(
                    oldItem: CoinWithdrawCoinListModel,
                    newItem: CoinWithdrawCoinListModel
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(
                    oldItem: CoinWithdrawCoinListModel,
                    newItem: CoinWithdrawCoinListModel
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }


}