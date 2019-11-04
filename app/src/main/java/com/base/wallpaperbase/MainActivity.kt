package com.base.wallpaperbase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import com.base.baselibrary.activity.BaseActivity
import com.base.baselibrary.dao.MediaDao
import com.base.wallpaperbase.databinding.ActivityMainBinding
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.video.Video
import com.base.wallpaperbase.viewmodel.AppViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {

    private val viewModel = AppViewModel.getInstance()
    private lateinit var mediaDao:MediaDao
    private val TAG = "NVT"+javaClass.simpleName

    private val listPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.isGrantPermission = checkPermission(listPermission)
        mediaDao = MediaDao(this)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        viewModel.listImage.observe(this, Observer {
            Log.e(TAG,"List Image: $it, ${it.size}")
        })
        viewModel.listImage.observe(this, Observer {
            Log.e(TAG,"List Video: $it")
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnGrantPermission -> {
                doRequestPermission(
                    listPermission, {
                        binding.isGrantPermission = true
                        viewModel.listImage.value = mediaDao.getMedia(Image::class.java)
                        viewModel.listVideo.value = mediaDao.getMedia(Video::class.java)
                    })
            }
        }
    }

}
