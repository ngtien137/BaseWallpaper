package com.base.wallpaperbase.model.image

import com.base.baselibrary.adapter.BaseAdapter

interface ImageListener : BaseAdapter.ListItemListener {
    fun onMediaClick(image: Image){}
}