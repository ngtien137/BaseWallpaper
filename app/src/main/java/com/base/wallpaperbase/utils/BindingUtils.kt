package com.base.wallpaperbase.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("img_path")
fun loadImage(imageView:ImageView,img_path:String){
    val size = imageView.context.resources.displayMetrics.widthPixels/3
    Glide.with(imageView).load(img_path).override(size).into(imageView)
}