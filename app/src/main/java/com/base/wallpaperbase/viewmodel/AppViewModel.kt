package com.base.wallpaperbase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.video.Video

class AppViewModel:ViewModel(){
    companion object{
        private val viewModelFactory = ViewModelFactory()
        fun getInstance() = viewModelFactory.create(AppViewModel::class.java)
    }

    val listImage = MutableLiveData<List<Image>>()
    val listVideo = MutableLiveData<List<Video>>()
}