package com.clone_coding.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (url != null) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}

@BindingAdapter("formattedNumber")
fun formatNumber(textView: TextView, number: Int) {
    textView.text = String.format("%,d", number)
}