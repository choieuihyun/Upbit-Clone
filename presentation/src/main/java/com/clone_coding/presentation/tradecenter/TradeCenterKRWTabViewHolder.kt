package com.clone_coding.presentation.tradecenter


import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.TestModel
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentTradeListItemBinding

class TradeCenterKRWTabViewHolder(
    private val binding: FragmentTradeListItemBinding,
    private val coinTickerMap: Map<String, String>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TestModel) {

        binding.krwTab = data

        val code = data.code

        binding.listKoreanName.text = coinTickerMap[code] ?: "Unknown"
        val redColor = ContextCompat.getColor(binding.listComparePreviousDay.context, R.color.red)
        val whiteColor = ContextCompat.getColor(binding.listComparePreviousDay.context, R.color.white)
        val blueColor = ContextCompat.getColor(binding.listComparePreviousDay.context, R.color.blue)

        val signedChangeRate = data.signedChangeRate?.toDouble()
        Log.d("sdfsdf", signedChangeRate.toString())

        if(signedChangeRate!! < 0.0) {
            binding.listComparePreviousDay.setTextColor(blueColor)
        } else if (signedChangeRate == 0.0) {
            binding.listComparePreviousDay.setTextColor(whiteColor)
        } else {
            binding.listComparePreviousDay.setTextColor(redColor)
        }


    }


}

