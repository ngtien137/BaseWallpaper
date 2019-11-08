package com.base.wallpaperbase.model.resource

import com.base.baselibrary.model.ModelBase
import com.base.wallpaperbase.BuildConfig
import com.base.wallpaperbase.utils.App
import java.io.File

class ObjectResource(var id : Int = 0) : ModelBase(){

    fun getName():String{
        return App.app.resources.getResourceEntryName(id)
    }

    override fun toString(): String {
        return "Image [$id], ${getName()}"
    }
}