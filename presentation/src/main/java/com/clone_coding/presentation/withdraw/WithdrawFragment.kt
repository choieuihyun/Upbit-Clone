package com.clone_coding.presentation.withdraw

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentWithdrawBinding
import com.clone_coding.presentation.tradecenter.TradeCenterRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawFragment: BaseFragment<FragmentWithdrawBinding>(R.layout.fragment_withdraw) {

    private val viewModel: WithdrawViewModel by viewModels()

    private lateinit var withdrawListAdapter: WithdrawRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("mergedListState", "sibal")

        setupRecyclerView() // 여기 Create로 못올리는 이유 : BaseFragment에서 binding을 초기화시키는 부분을 봐라.

        binding.withdrawViewModel = viewModel

        viewModel.getWithDrawList()

        viewModel.withdrawList.observe(viewLifecycleOwner) {

            Log.d("mergedListFragment", it.toString())
            withdrawListAdapter.submitList(it)

        }

    }

    override fun onResume() {
        super.onResume()

    }

    private fun setupRecyclerView() {

        withdrawListAdapter = WithdrawRecyclerViewAdapter()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = withdrawListAdapter
        }

    }

}