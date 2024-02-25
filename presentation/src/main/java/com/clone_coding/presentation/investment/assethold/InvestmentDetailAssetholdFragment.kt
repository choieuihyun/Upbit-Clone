package com.clone_coding.presentation.investment.assethold

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailAssetheldBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvestmentDetailAssetholdFragment: BaseFragment<FragmentInvestmentDetailAssetheldBinding>(R.layout.fragment_investment_detail_assetheld) {

    private val viewModel: InvestmentDetailAssetholdViewModel by viewModels()

    private lateinit var investmentAssetHoldAdapter: InvestmentDetailAssetholdAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.investmentAssetHoldViewModel = viewModel

        viewModel.getInvestmentAssetHold()

        viewModel.assetHoldList.observe(viewLifecycleOwner) {

            investmentAssetHoldAdapter.submitList(it)

        }
    }

    private fun setupRecyclerView() {

        investmentAssetHoldAdapter = InvestmentDetailAssetholdAdapter()

        binding.assetHoldRecyclerView.apply {
            // setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            // isNestedScrollingEnabled = false;
            adapter = investmentAssetHoldAdapter
        }


    }
}