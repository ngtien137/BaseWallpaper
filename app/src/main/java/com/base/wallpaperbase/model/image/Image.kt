package com.base.wallpaperbase.model.image

import android.provider.MediaStore
import com.base.wallpaperbase.model.Media

class Image : Media(){
    override fun getUri() = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    override fun toString(): String {
        return "Image [$path]"
    }
}