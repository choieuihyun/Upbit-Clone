package com.clone_coding.presentation.tradecenter

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.MainActivity
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentTradeBinding
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class TradeCenterFragment : BaseFragment<FragmentTradeBinding>(R.layout.fragment_trade) {

    private val tradeCenterKRWTabViewModel: TradeCenterKRWTabViewModel by viewModels()
    private lateinit var krwTabListAdapter: TradeCenterRecyclerViewAdapter
    private lateinit var mainActivity: MainActivity

    // 텍스트 클릭 구분을 위한 상태 변수
    private var transactionAmountSelected = false
    private var comparePreviousDaySelected = false
    private var currentPriceSelected = false


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // 텍스트 클릭 상태 세팅 ( 현재가 , 전일대비, 거래대금 )
        val coinInfoTexts = arrayOf(
            binding.currentPrice,
            binding.transactionAmount,
            binding.comparePreviousDay
        )

        val originalTextColor = binding.transactionAmount.textColors

//        binding.transactionAmount.setOnClickListener { onClickListener(it, originalTextColor) }
//        binding.comparePreviousDay.setOnClickListener { onClickListener(it, originalTextColor) }
//        binding.currentPrice.setOnClickListener { onClickListener(it, originalTextColor) }

        setupClickListeners(originalTextColor)

        // 초기에 데이터 들어와있어야 하므로 초기 데이터 리스트 생성.
        tradeCenterKRWTabViewModel.marketData.observe(viewLifecycleOwner) { data ->

            data?.let {
                Log.d("datadata", it.toString())
                krwTabListAdapter.submitList(it)
            }
        }

        tradeCenterKRWTabViewModel.getTradeCenterRealTimeInfo()


    }

    override fun onResume() {
        super.onResume()

    }

    private fun setupClickListeners(originalColor: ColorStateList) {

        binding.transactionAmount.setOnClickListener {
            onClickListener(it, originalColor)
            tradeCenterKRWTabViewModel.setSortType(TradeCenterKRWTabViewModel.SortType.AMOUNT)
        }
        binding.comparePreviousDay.setOnClickListener {
            onClickListener(it, originalColor)
            tradeCenterKRWTabViewModel.setSortType(TradeCenterKRWTabViewModel.SortType.COMPARE_PREVIOUS_DAY)
        }
        binding.currentPrice.setOnClickListener {
            onClickListener(it, originalColor)
            tradeCenterKRWTabViewModel.setSortType(TradeCenterKRWTabViewModel.SortType.CURRENT_PRICE)
        }

    }


    private fun onClickListener(clickedText: View, originalColor: ColorStateList) {
        val clickedId = clickedText.id

        // 현재 클릭된 텍스트의 클릭 상태에 따라 처리
        when (clickedId) {
            binding.transactionAmount.id -> {
                if (!binding.transactionAmount.isSelected) {

                    // 현재 클릭된 텍스트의 클릭 상태 변경
                    binding.transactionAmount.isSelected = true
                    tradeCenterKRWTabViewModel.transactionAmountState = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.comparePreviousDay.isSelected = false
                    tradeCenterKRWTabViewModel.comparePreviousDayState = false
                    binding.comparePreviousDay.setTextColor(originalColor)

                    binding.currentPrice.isSelected = false
                    tradeCenterKRWTabViewModel.currentPriceState = false
                    binding.currentPrice.setTextColor(originalColor)


                } else {

                    binding.transactionAmount.isSelected = false
                    tradeCenterKRWTabViewModel.transactionAmountState = false
                    (clickedText as TextView).setTextColor(originalColor)

                }

            }

            binding.comparePreviousDay.id -> {
                if (!binding.comparePreviousDay.isSelected) {

                    binding.comparePreviousDay.isSelected = true
                    tradeCenterKRWTabViewModel.comparePreviousDayState = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    binding.transactionAmount.isSelected = false
                    tradeCenterKRWTabViewModel.transactionAmountState = false
                    binding.transactionAmount.setTextColor(originalColor)

                    binding.currentPrice.isSelected = false
                    tradeCenterKRWTabViewModel.currentPriceState = false
                    binding.currentPrice.setTextColor(originalColor)
                } else {

                    binding.comparePreviousDay.isSelected = false
                    tradeCenterKRWTabViewModel.comparePreviousDayState = false
                    (clickedText as TextView).setTextColor(originalColor)

                }

            }

            binding.currentPrice.id -> {
                if (!binding.currentPrice.isSelected) {

                    binding.currentPrice.isSelected = true
                    tradeCenterKRWTabViewModel.currentPriceState = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    binding.transactionAmount.isSelected = false
                    tradeCenterKRWTabViewModel.transactionAmountState = false
                    binding.transactionAmount.setTextColor(originalColor)

                    binding.comparePreviousDay.isSelected = false
                    tradeCenterKRWTabViewModel.comparePreviousDayState = false
                    binding.comparePreviousDay.setTextColor(originalColor)
                } else {

                    binding.currentPrice.isSelected = false
                    tradeCenterKRWTabViewModel.currentPriceState = false
                    (clickedText as TextView).setTextColor(originalColor)

                }

            }
        }
    }

    private fun setupRecyclerView() {

        krwTabListAdapter = TradeCenterRecyclerViewAdapter()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null // 깜빡임 제거
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = krwTabListAdapter
        }

        krwTabListAdapter.setOnItemClickListener { item ->

            val action =
                TradeCenterFragmentDirections.actionTradeCenterFragmentToTradeCenterDetailFragment(
                    item
                )
            findNavController().navigate(action)

        }

    }


}