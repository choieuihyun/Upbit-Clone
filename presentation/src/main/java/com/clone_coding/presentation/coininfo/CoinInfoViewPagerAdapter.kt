package com.clone_coding.presentation.coininfo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clone_coding.presentation.coininfo.coinnews.CoinInfoCoinNewsFragment
import com.clone_coding.presentation.coininfo.cointrend.CoinInfoCoinTrendFragment

class CoinInfoViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> return CoinInfoCoinTrendFragment()
            1 -> return CoinInfoCoinNewsFragment()
            else -> CoinInfoCoinTrendFragment()
        }
    }

}