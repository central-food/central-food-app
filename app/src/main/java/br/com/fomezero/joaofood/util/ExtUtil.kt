package br.com.fomezero.joaofood.util

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions().apply {
        placeholder(progressDrawable)
        error(R.drawable.ic_baseline_broken_image_24)
    }

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}