package com.base.wallpaperbase.model

import com.base.baselibrary.adapter.BaseAdapter

interface MediaListener : BaseAdapter.ListItemListener {
    fun onMediaClick(media: Media){}
}