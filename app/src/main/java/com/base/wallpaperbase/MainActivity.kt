package com.base.wallpaperbase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionManager
import com.base.baselibrary.activity.BaseActivity
import com.base.baselibrary.dao.MediaDao
import com.base.wallpaperbase.databinding.ActivityMainBinding
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.resource.ObjectResource
import com.base.wallpaperbase.model.video.Video
import com.base.wallpaperbase.viewmodel.AppViewModel
import com.base.wallpaperbase.views.ImageFragment
import com.base.wallpaperbase.views.ObjectResourceFragment
import com.base.wallpaperbase.views.VideoFragment


class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {

    companion object{
        const val REQUEST_SETLIVE = 100
    }

    private lateinit var viewModel:AppViewModel
    private lateinit var mediaDao:MediaDao
    private val TAG = "NVT"+javaClass.simpleName
    private var listFragment = arrayListOf<Fragment>(ImageFragment(),VideoFragment(),ObjectResourceFragment())

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
        binding.isLoading = false
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
            R.id.btnResource->{
                replaceScreen(2)
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
        val fields = R.raw::class.java.fields
        viewModel.listResource.value = ArrayList<ObjectResource>().apply {
            for (item in fields){
                add(ObjectResource(item.getInt(item)))
            }
            add(ObjectResource(R.drawable.image1))
            add(ObjectResource(R.drawable.image2))
            add(ObjectResource(R.drawable.image3))
            add(ObjectResource(R.drawable.image4))
            add(ObjectResource(R.drawable.image5))
            add(ObjectResource(R.drawable.image6))
            add(ObjectResource(R.drawable.image7))
            add(ObjectResource(R.drawable.image8))
            add(ObjectResource(R.drawable.image9))
            add(ObjectResource(R.drawable.image10))
        }
    }

    fun showLoading(isShown:Boolean = true){
        Log.e(TAG,"ShowLoading: $isShown")
        binding.isLoading = isShown
    }

    override fun onBackPressed() {
        if (binding.isLoading==true)
            return
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_SETLIVE->{
                when(resultCode){
                    0->{
                        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                    }
                    -1->{
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
