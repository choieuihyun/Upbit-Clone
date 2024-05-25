package com.clone_coding.presentation.tradecenter.detail_chart

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.clone_coding.domain.model.TradeCenterKRWTabModel
import com.clone_coding.presentation.BaseFragment
import com.clone_coding.presentation.R
import com.clone_coding.presentation.coininfo.CoinInfoViewPagerAdapter
import com.clone_coding.presentation.databinding.FragmentTradeDetailBinding
import com.clone_coding.presentation.tradecenter.TradeCenterKRWTabViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeCenterDetailFragment() :
    BaseFragment<FragmentTradeDetailBinding>(R.layout.fragment_trade_detail) {

    private val args by navArgs<TradeCenterDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val krwTabModel = args.KRWTabModel

        val viewPagerAdapter = TradeCenterDetailViewPagerAdapter(this, krwTabModel!!)
        binding.viewPager.adapter = viewPagerAdapter

        val originalTextColor = binding.day.textColors

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "주문"
                1 -> tab.text = "호가"
                2 -> tab.text = "차트"
                3 -> tab.text = "시세"
                4 -> tab.text = "정보"
            }
        }.attach()

        setupClickListener(originalTextColor)
    }

    private fun setupClickListener(originalColor: ColorStateList) {

        binding.minute.setOnClickListener {
            onClickListener(it, originalColor)

        }
        binding.day.setOnClickListener {
            onClickListener(it, originalColor)

        }
        binding.week.setOnClickListener {
            onClickListener(it, originalColor)

        }
        binding.month.setOnClickListener {
            onClickListener(it, originalColor)

        }

    }


    private fun onClickListener(clickedText: View, originalColor: ColorStateList) {
        val clickedId = clickedText.id

        // 현재 클릭된 텍스트의 클릭 상태에 따라 처리
        when (clickedId) {
            binding.minute.id -> {
                if (!binding.minute.isSelected) {

                    // 현재 클릭된 텍스트의 클릭 상태 변경
                    binding.minute.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.day.isSelected = false
                    binding.day.setTextColor(originalColor)

                    binding.week.isSelected = false
                    binding.week.setTextColor(originalColor)

                    binding.month.isSelected = false
                    binding.month.setTextColor(originalColor)


                } else {

                    binding.minute.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)

                }

            }

            binding.day.id -> {
                if (!binding.day.isSelected) {

                    binding.day.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.minute.isSelected = false
                    binding.minute.setTextColor(originalColor)

                    binding.week.isSelected = false
                    binding.week.setTextColor(originalColor)

                    binding.month.isSelected = false
                    binding.month.setTextColor(originalColor)
                } else {

                    binding.day.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)

                }

            }

            binding.week.id -> {
                if (!binding.week.isSelected) {

                    binding.week.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.minute.isSelected = false
                    binding.minute.setTextColor(originalColor)

                    binding.day.isSelected = false
                    binding.day.setTextColor(originalColor)

                    binding.month.isSelected = false
                    binding.month.setTextColor(originalColor)
                } else {

                    binding.week.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)

                }

            }

            binding.month.id -> {
                if (!binding.month.isSelected) {

                    binding.month.isSelected = true
                    (clickedText as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )

                    // 이전에 선택된 텍스트의 클릭 상태 초기화
                    binding.minute.isSelected = false
                    binding.minute.setTextColor(originalColor)

                    binding.day.isSelected = false
                    binding.day.setTextColor(originalColor)

                    binding.week.isSelected = false
                    binding.week.setTextColor(originalColor)
                } else {

                    binding.month.isSelected = false
                    (clickedText as TextView).setTextColor(originalColor)

                }


            }
        }
    }

}