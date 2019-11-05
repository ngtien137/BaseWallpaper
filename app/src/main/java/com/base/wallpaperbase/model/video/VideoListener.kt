package com.base.wallpaperbase.model.video

import com.base.baselibrary.adapter.BaseAdapter

interface VideoListener : BaseAdapter.ListItemListener {
    fun onMediaClick(video: Video){}
}