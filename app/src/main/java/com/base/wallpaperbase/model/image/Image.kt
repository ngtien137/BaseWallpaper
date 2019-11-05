package com.base.wallpaperbase.model.image

import android.provider.MediaStore
import com.base.baselibrary.model.MediaInfo
import com.base.baselibrary.model.MediaModelBase

class Image : MediaModelBase(){

    @MediaInfo(MediaStore.Images.ImageColumns._ID)
    var id : Long = 0
    @MediaInfo(MediaStore.Images.ImageColumns.DATA)
    var path:String = ""
    @MediaInfo(MediaStore.Images.ImageColumns.DISPLAY_NAME)
    var displayName:String = ""
    @MediaInfo(MediaStore.Images.ImageColumns.TITLE)
    var title:String = ""

    override fun getUri() = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    override fun toString(): String {
        return "Image [$path]"
    }
}