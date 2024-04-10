package com.clone_coding.presentation.coininfo.coinnews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentCoinInfoCoinnewsMainnewsViewpagerOneBinding

class MainNewsViewPagerItemOne: BaseFragment<FragmentCoinInfoCoinnewsMainnewsViewpagerOneBinding>
    (R.layout.fragment_coin_info_coinnews_mainnews_viewpager_one) {

    private val viewModel: CoinInfoCoinNewsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainNewsViewModel = viewModel


    }


}