package com.clone_coding.presentation.tradecenter.detail_chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.presentation.coininfo.coinnews.CoinInfoCoinNewsFragment
import com.clone_coding.presentation.coininfo.cointrend.CoinInfoCoinTrendFragment

class TradeCenterDetailViewPagerAdapter(fragment: Fragment, private val krwTabModel: TradeCenterKRWTabModel): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                val fragment = TradeCenterDetailChartFragment()
                fragment.arguments = Bundle().apply {
                    putSerializable("chartModel", krwTabModel)
                }
                return fragment
            }
            1 -> {
                val fragment = TradeCenterDetailChartFragment()
                fragment.arguments = Bundle().apply {
                    putSerializable("chartModel", krwTabModel)
                }
                return fragment
            }
            2 -> {
                val fragment = TradeCenterDetailChartFragment()
                fragment.arguments = Bundle().apply {
                    putSerializable("chartModel", krwTabModel)
                }
                return fragment
            }
            3 -> {
                val fragment = TradeCenterDetailChartFragment()
                fragment.arguments = Bundle().apply {
                    putSerializable("chartModel", krwTabModel)
                }
                return fragment
            }
            4 -> {
                val fragment = TradeCenterDetailChartFragment()
                fragment.arguments = Bundle().apply {
                    putSerializable("chartModel", krwTabModel)
                }
                return fragment
            }
            else -> TradeCenterDetailChartFragment()
        }
    }

}