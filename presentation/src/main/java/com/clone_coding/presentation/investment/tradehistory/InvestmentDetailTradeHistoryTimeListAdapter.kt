package com.clone_coding.presentation.investment.tradehistory

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinInvestmentTradeHistoryTestModel
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailTradehistoryTimelistBinding

class InvestmentDetailTradeHistoryTimeListAdapter: ListAdapter<CoinInvestmentTradeHistoryTestModel,
        InvestmentDetailTradeHistoryTimeListAdapter.InvestmentDetailTradeHistoryTimeListViewHolder>(
    diffCallback) {

    lateinit var items: MutableList<CoinInvestmentTradeHistoryTestModel>

    fun build(i: MutableList<CoinInvestmentTradeHistoryTestModel>): InvestmentDetailTradeHistoryTimeListAdapter {

        items = i

        return this
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvestmentDetailTradeHistoryTimeListViewHolder {

        return InvestmentDetailTradeHistoryTimeListViewHolder(
            FragmentInvestmentDetailTradehistoryTimelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(
        holder: InvestmentDetailTradeHistoryTimeListViewHolder,
        position: Int
    ) {

        val tradeHistoryTimeList = currentList[position]

        holder.bind(tradeHistoryTimeList)

    }

    inner class InvestmentDetailTradeHistoryTimeListViewHolder(val binding: FragmentInvestmentDetailTradehistoryTimelistBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: CoinInvestmentTradeHistoryTestModel) {

                with(binding) {

                    conclusionTime.text = item.conclusionTime
                    coinName.text = item.coinName
                    timeListTestModel = item

                    tradeHistoryTradeListRecyclerView.apply {

                        adapter = InvestmentDetailTradeHistoryTradeListAdapter().apply {
                            submitList(item.tradeList)
                        }

                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                    }

                }

            }

        }

    companion object {

        private val diffCallback =
            object : DiffUtil.ItemCallback<CoinInvestmentTradeHistoryTestModel>() {

                override fun areItemsTheSame(
                    oldItem: CoinInvestmentTradeHistoryTestModel,
                    newItem: CoinInvestmentTradeHistoryTestModel
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(
                    oldItem: CoinInvestmentTradeHistoryTestModel,
                    newItem: CoinInvestmentTradeHistoryTestModel
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }


}