package com.clone_coding.presentation.tradecenter.detail_chart

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.coininfo.CoinInfoViewPagerAdapter
import com.clone_coding.presentation.databinding.FragmentTradeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeCenterDetailFragment() : BaseFragment<FragmentTradeDetailBinding>(R.layout.fragment_trade_detail) {

    private val args by navArgs<TradeCenterDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val krwTabModel = args.KRWTabModel

        val viewPagerAdapter = TradeCenterDetailViewPagerAdapter(this, krwTabModel!!)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "주문"
                1 -> tab.text = "호가"
                2 -> tab.text = "차트"
                3 -> tab.text = "시세"
                4 -> tab.text = "정보"
            }
        }.attach()
    }

}