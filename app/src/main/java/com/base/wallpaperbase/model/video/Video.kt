package com.base.wallpaperbase.model.video

import android.provider.MediaStore
import com.base.baselibrary.model.MediaInfo
import com.base.baselibrary.model.MediaModelBase

class Video : MediaModelBase(){
    @MediaInfo(MediaStore.Video.VideoColumns._ID)
    var id : Long = 0
    @MediaInfo(MediaStore.Video.VideoColumns.DATA)
    var path:String = ""
    @MediaInfo(MediaStore.Video.VideoColumns.DISPLAY_NAME)
    var displayName:String = ""
    @MediaInfo(MediaStore.Video.VideoColumns.TITLE)
    var title:String = ""
    override fun getUri() = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    override fun toString(): String {
        return "Video [$path]"
    }
}