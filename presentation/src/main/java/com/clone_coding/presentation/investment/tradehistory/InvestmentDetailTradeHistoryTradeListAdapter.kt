package com.clone_coding.presentation.investment.tradehistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinInvestmentTradeHistoryInnerTestModel
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailTradehistoryTradelistBinding

class InvestmentDetailTradeHistoryTradeListAdapter: ListAdapter<CoinInvestmentTradeHistoryInnerTestModel,
        InvestmentDetailTradeHistoryTradeListAdapter.InvestmentDetailTradeHistoryTradeListViewHolder>(
    diffCallback) {

//    lateinit var items: MutableList<CoinInvestmentTradeHistoryInnerTestModel>

//    fun build(i: MutableList<CoinInvestmentTradeHistoryInnerTestModel>): InvestmentDetailTradeHistoryTradeListAdapter {
//
//        items = i
//
//        return this
//
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvestmentDetailTradeHistoryTradeListViewHolder {

        return InvestmentDetailTradeHistoryTradeListViewHolder(
            FragmentInvestmentDetailTradehistoryTradelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(
        holder: InvestmentDetailTradeHistoryTradeListViewHolder,
        position: Int
    ) {

        val tradeHistoryTradeList = currentList[position]

        holder.bind(tradeHistoryTradeList)

    }

    inner class InvestmentDetailTradeHistoryTradeListViewHolder(val binding: FragmentInvestmentDetailTradehistoryTradelistBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinInvestmentTradeHistoryInnerTestModel) {

            binding.tradeListTestModel = item

//            binding.market.text = item.market
//            binding.type.text = item.type
//            binding.tradePrice.text = item.tradePrice
//            binding.charge.text = item.charge
//            binding.orderTime.text = item.orderTime
//            binding.settlementAmount.text = item.settlementAmount
//            binding.tradeAmount.text = item.tradeAmount
//            binding.tradeQuantity.text = item.tradeQuantity 아 이걸 왜 이렇게 하고있어!~!!!

        }


    }

    companion object {

        private val diffCallback =
            object : DiffUtil.ItemCallback<CoinInvestmentTradeHistoryInnerTestModel>() {

                override fun areItemsTheSame(
                    oldItem: CoinInvestmentTradeHistoryInnerTestModel,
                    newItem: CoinInvestmentTradeHistoryInnerTestModel
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(
                    oldItem: CoinInvestmentTradeHistoryInnerTestModel,
                    newItem: CoinInvestmentTradeHistoryInnerTestModel
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }

}