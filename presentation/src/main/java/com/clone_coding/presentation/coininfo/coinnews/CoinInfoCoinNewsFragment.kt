package com.clone_coding.presentation.coininfo.coinnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.coininfo.cointrend.CoinInfoCoinTrendViewModel
import com.clone_coding.presentation.databinding.FragmentCoinInfoTabCoinnewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinInfoCoinNewsFragment: BaseFragment<FragmentCoinInfoTabCoinnewsBinding>(R.layout.fragment_coin_info_tab_coinnews) {

    private val viewModel: CoinInfoCoinNewsViewModel by activityViewModels()

    private lateinit var latestNewsAdapter: CoinInfoCoinNewsLatestNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMainNews()
        viewModel.getLatestCoinNews()

        setupLatestNewsRecyclerView()

        val viewPagerAdapter = CoinInfoCoinNewsMainNewsViewPagerAdapter(this)
        binding.mainNewsViewPager.adapter = viewPagerAdapter

        viewModel.latestNewsList.observe(viewLifecycleOwner) {

            latestNewsAdapter.submitList(it)

        }

        binding.coinText.setOnClickListener {

            changeLatestNewsRecyclerView {

                viewModel.getLatestCoinNews()

            }

        }

        binding.techText.setOnClickListener {

            changeLatestNewsRecyclerView {

                viewModel.getLatestTechNews()

            }

        }

        binding.policyText.setOnClickListener {

            changeLatestNewsRecyclerView {

                viewModel.getLatestPolicyNews()

            }

        }

        binding.columnText.setOnClickListener {

            changeLatestNewsRecyclerView {

                viewModel.getLatestColumnNews()

            }

        }





    }

    private fun setupLatestNewsRecyclerView() {

        latestNewsAdapter = CoinInfoCoinNewsLatestNewsAdapter()

        binding.latestNewsRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = latestNewsAdapter
        }


    }

    private fun changeLatestNewsRecyclerView(getNewsFunction: () -> Unit) {

        getNewsFunction()

        viewModel.latestNewsList.observe(viewLifecycleOwner) {
            latestNewsAdapter.submitList(it)
        }

    }

}