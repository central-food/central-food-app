package br.com.fomezero.joaofood

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions().apply {
        placeholder(progressDrawable)
        error(R.mipmap.ic_launcher_round)
    }

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}