package com.clone_coding.presentation.investment

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.clone_coding.presentation.R

@BindingAdapter("profitLossTextColor")
fun profitLossTextColor(textView: TextView, evaluationProfitLoss: Double) {

    val colorRes = when {
        evaluationProfitLoss > 0 -> R.color.red
        evaluationProfitLoss < 0 -> R.color.blue
        else -> R.color.white
    }

    textView.setTextColor(ContextCompat.getColor(textView.context, colorRes))

}

@BindingAdapter("profitLossPercentageTextColor")
fun profitLossPercentageTextColor(textView: TextView, profitPercentageLoss: Double) {

    val colorRes = when {
        profitPercentageLoss > 0.0 -> R.color.red
        profitPercentageLoss < 0.0 -> R.color.blue
        else -> R.color.white
    }

    textView.setTextColor(ContextCompat.getColor(textView.context, colorRes))

}