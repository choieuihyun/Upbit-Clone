package com.clone_coding.presentation.investment.assethold

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel
import com.clone_coding.presentation.R
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailAssetheldViewholderBinding
import java.math.BigDecimal
import java.math.RoundingMode

class InvestmentDetailAssetholdViewHolder(
    private val binding: FragmentInvestmentDetailAssetheldViewholderBinding,
    private val coinTickerMap: Map<String,String>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CoinInvestmentAssetHoldModel) {

        binding.assetHold = data

        val code = data.currency
        val evaluationProfitLoss = data.evaluationProfitLoss.toInt()
        val profitLossPercentage = data.profitLossPercentage
        var parseProfitLossPercentage = 0.0

        if(profitLossPercentage != "NaN") {
            parseProfitLossPercentage = BigDecimal(profitLossPercentage).setScale(4, RoundingMode.HALF_UP).toDouble() * 100
        } else {
            parseProfitLossPercentage = 0.0
        }

        Log.d("viewHolderPercent", parseProfitLossPercentage.toString())

        binding.koreanName.text = coinTickerMap[code] ?: "비상장"
        binding.profitLossPercentage.text = "$parseProfitLossPercentage%"

        if(evaluationProfitLoss < 0) {
            binding.evaluationProfitLoss.setTextColor(ContextCompat.getColor(binding.evaluationProfitLoss.context, R.color.blue))
        } else if (evaluationProfitLoss > 0) {
            binding.evaluationProfitLoss.setTextColor(ContextCompat.getColor(binding.evaluationProfitLoss.context, R.color.red))
        } else {
            binding.evaluationProfitLoss.setTextColor(ContextCompat.getColor(binding.evaluationProfitLoss.context, R.color.white))
        }

        if(parseProfitLossPercentage < 0) {
            binding.profitLossPercentage.setTextColor(ContextCompat.getColor(binding.profitLossPercentage.context, R.color.blue))
        } else if (parseProfitLossPercentage > 0) {
            binding.profitLossPercentage.setTextColor(ContextCompat.getColor(binding.profitLossPercentage.context, R.color.red))
        } else {
            binding.profitLossPercentage.setTextColor(ContextCompat.getColor(binding.profitLossPercentage.context, R.color.white))
        }


    }

}