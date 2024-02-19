package com.clone_coding.presentation.coininfo.cointrend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentCoinInfoTabCointrendBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinInfoCoinTrendFragment : BaseFragment<FragmentCoinInfoTabCointrendBinding>(R.layout.fragment_coin_info_tab_cointrend) {

    private val viewModel: CoinInfoCoinTrendViewModel by viewModels()
    private lateinit var highestIncreaseRateAdapter: CoinInfoCoinTrendHighestIncreaseRateAdapter
    private lateinit var volumePowerRateAdapter: CoinInfoCoinTrendHighestVolumePowerRateAdapter
    private lateinit var highestMarketCapAdapter: CoinInfoCoinTrendHighestMarketCapAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getCoinInfoCoinTrendHighestRate()

        viewModel.highestIncreaseFiveRate.observe(viewLifecycleOwner) { data ->

            data.let {
                highestIncreaseRateAdapter.submitList(it)
            }

        }

        viewModel.volumePowerRateList.observe(viewLifecycleOwner) { data ->

            data.let {
                volumePowerRateAdapter.submitList(it)
            }

        }

        viewModel.marketCapHighestList.observe(viewLifecycleOwner) { data ->

            data.let {
                highestMarketCapAdapter.submitList(it)
            }

        }

    }


    private fun setupRecyclerView() {

        highestIncreaseRateAdapter = CoinInfoCoinTrendHighestIncreaseRateAdapter()
        volumePowerRateAdapter = CoinInfoCoinTrendHighestVolumePowerRateAdapter()
        highestMarketCapAdapter = CoinInfoCoinTrendHighestMarketCapAdapter()

        binding.increaseRateList.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = highestIncreaseRateAdapter
        }

        binding.volumePowerList.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = volumePowerRateAdapter
        }

        binding.marketCapList.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = highestMarketCapAdapter
        }

    }
}