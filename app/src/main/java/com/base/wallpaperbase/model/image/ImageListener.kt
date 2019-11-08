package com.base.wallpaperbase.model.image

import com.base.baselibrary.adapter.BaseAdapter
import com.base.wallpaperbase.model.image.Image

interface ImageListener : BaseAdapter.ListItemListener {
    fun onMediaClick(image: Image){}
}