package com.sebastianjoya.twitunab.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadImage(imageView: ImageView, url:String){
    Glide.with(imageView.context).load(url).into(imageView)
}