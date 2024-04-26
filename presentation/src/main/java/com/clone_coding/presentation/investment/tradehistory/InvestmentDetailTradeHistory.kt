package com.clone_coding.presentation.investment.tradehistory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailTradehistoryBinding
import com.clone_coding.presentation.tradecenter.TradeCenterRecyclerViewAdapter

class InvestmentDetailTradeHistory : BaseFragment<FragmentInvestmentDetailTradehistoryBinding>
    (R.layout.fragment_investment_detail_tradehistory) {

    lateinit var tradeHistoryAdapter: InvestmentDetailTradeHistoryTimeListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        tradeHistoryAdapter.submitList(InvestmentDetailTradeHistoryTestData.coinTradeHistoryList)

    }

    private fun setupRecyclerView() {

        tradeHistoryAdapter = InvestmentDetailTradeHistoryTimeListAdapter()

        binding.tradeHistoryRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = tradeHistoryAdapter
        }

    }
}