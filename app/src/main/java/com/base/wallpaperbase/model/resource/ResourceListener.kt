package com.base.wallpaperbase.model.resource

import com.base.baselibrary.adapter.BaseAdapter

interface ResourceListener : BaseAdapter.ListItemListener {
    fun onResourceClick(resource: ObjectResource){}
}