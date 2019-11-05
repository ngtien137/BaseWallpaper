package com.base.wallpaperbase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionManager
import com.base.baselibrary.activity.BaseActivity
import com.base.baselibrary.dao.MediaDao
import com.base.baselibrary.fragment.BaseFragment
import com.base.baselibrary.fragment.BaseTitleFragment
import com.base.wallpaperbase.databinding.ActivityMainBinding
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.video.Video
import com.base.wallpaperbase.viewmodel.AppViewModel
import com.base.wallpaperbase.views.ImageFragment
import com.base.wallpaperbase.views.VideoFragment

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {

    private lateinit var viewModel:AppViewModel
    private lateinit var mediaDao:MediaDao
    private val TAG = "NVT"+javaClass.simpleName
    private var listFragment = arrayListOf<Fragment>(ImageFragment(),VideoFragment())

    private val listPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaDao = MediaDao(this)
        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]
        binding.clickListener = this
        binding.isGrantPermission = checkPermission(listPermission)
        if (binding.isGrantPermission==true){
            loadData()
        }
        mediaDao = MediaDao(this)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnGrantPermission -> {
                doRequestPermission(
                    listPermission, {
                        binding.isGrantPermission = true
                        loadData()
                    })
            }
            R.id.btnImage->{
                replaceScreen(0)
            }
            R.id.btnVideo->{
                replaceScreen(1)
            }
        }
    }

    fun replaceScreen(index:Int){
        val fragment = listFragment[index]
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain,fragment)
            addToBackStack(fragment.javaClass.simpleName)
            commit()
        }
    }

    private fun loadData(){
        viewModel.listImage.value = ArrayList<Image>().apply {
            addAll(mediaDao.getMedia(Image::class.java))
        }
        viewModel.listVideo.value = ArrayList<Video>().apply {
            addAll(mediaDao.getMedia(Video::class.java))
        }
    }

}
