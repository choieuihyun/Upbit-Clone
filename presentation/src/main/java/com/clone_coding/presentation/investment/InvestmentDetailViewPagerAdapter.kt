package com.clone_coding.presentation.investment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clone_coding.presentation.investment.assethold.InvestmentDetailAssetholdFragment
import com.clone_coding.presentation.investment.tradehistory.InvestmentDetailTradeHistory

class InvestmentDetailViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return InvestmentDetailAssetholdFragment()
            1 -> return InvestmentDetailInvestmentProfitloss()
            2 -> return InvestmentDetailTradeHistory()
            3 -> return InvestmentDetailUnexecuted()
            else -> InvestmentDetailAssetholdFragment()
        }
    }

}