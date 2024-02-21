package com.clone_coding.presentation.investment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class InvestmentDetailFragment: BaseFragment<FragmentInvestmentDetailBinding>(R.layout.fragment_investment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = InvestmentDetailViewPagerAdapter(this)

        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.isUserInputEnabled = false

        binding.tabLayout.tabTextColors = ContextCompat.getColorStateList(requireContext() ,R.color.white)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "보유자산"
                1 -> tab.text = "투자손익"
                2 -> tab.text = "거래내역"
                3 -> tab.text = "미체결"
            }
        }.attach()
    }

}