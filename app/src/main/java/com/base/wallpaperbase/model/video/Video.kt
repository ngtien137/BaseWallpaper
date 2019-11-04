package com.base.wallpaperbase.model.video

import android.provider.MediaStore
import com.base.wallpaperbase.model.Media

class Video : Media(){
    override fun getUri() = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    override fun toString(): String {
        return "Video [$path]"
    }
}