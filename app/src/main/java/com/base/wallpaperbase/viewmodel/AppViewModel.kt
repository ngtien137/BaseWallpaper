package com.base.wallpaperbase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.video.Video

class AppViewModel:ViewModel(){
    val listImage = MutableLiveData<ArrayList<Image>>()
    val listVideo = MutableLiveData<ArrayList<Video>>()
}