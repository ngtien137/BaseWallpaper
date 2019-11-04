package com.base.wallpaperbase.model

import android.provider.MediaStore
import com.base.baselibrary.model.MediaInfo
import com.base.baselibrary.model.MediaModelBase

abstract class Media :MediaModelBase(){
    @MediaInfo(MediaStore.MediaColumns._ID)
    var id : Long = 0
    @MediaInfo(MediaStore.MediaColumns.DATA)
    var path:String = ""
    @MediaInfo(MediaStore.MediaColumns.DISPLAY_NAME)
    var displayName:String = ""
    @MediaInfo(MediaStore.MediaColumns.TITLE)
    var title:String = ""
}