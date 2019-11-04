package com.base.wallpaperbase.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("img_path")
fun ImageView.loadImage(path:String){
    val size = context.resources.displayMetrics.widthPixels/3
    Glide.with(context).load(path).override(size).into(this)
}