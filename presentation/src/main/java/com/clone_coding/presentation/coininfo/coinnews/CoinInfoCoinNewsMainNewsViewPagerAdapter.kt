package com.clone_coding.presentation.coininfo.coinnews

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clone_coding.presentation.coininfo.coinnews.CoinInfoCoinNewsFragment
import com.clone_coding.presentation.coininfo.cointrend.CoinInfoCoinTrendFragment

class CoinInfoCoinNewsMainNewsViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> MainNewsViewPagerItemOne()
            1 -> MainNewsViewPagerItemTwo()
            2 -> MainNewsViewPagerItemThree()
            else -> CoinInfoCoinNewsFragment()
        }
    }

}