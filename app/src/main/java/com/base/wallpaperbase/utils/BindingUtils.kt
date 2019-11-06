package com.base.wallpaperbase.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("img_path")
fun loadImage(view: View, img_path:String?){
    if (view is ImageView) {
        val size = view.context.resources.displayMetrics.widthPixels / 3
        Glide.with(view).load(img_path ?: "").override(size).into(view)
    }else if (view is TextView){
        view.text = File(img_path).extension
    }
}