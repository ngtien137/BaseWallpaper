package com.base.wallpaperbase.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.base.baselibrary.adapter.BaseAdapter
import com.base.baselibrary.fragment.BaseTitleFragment
import com.base.wallpaperbase.MainActivity
import com.base.wallpaperbase.R
import com.base.wallpaperbase.databinding.FragmentImageBinding
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.image.ImageListener
import com.base.wallpaperbase.utils.WallpaperAdapter
import com.base.wallpaperbase.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File
import java.util.*

class ImageFragment : BaseTitleFragment<FragmentImageBinding>(),
    ImageListener {

    private lateinit var adapter:BaseAdapter<Image>
    private lateinit var viewModel:AppViewModel
    private lateinit var activity:MainActivity
    private val TAG = "NVT"+javaClass.simpleName

    override fun getTitle() = "Images"

    override fun getLayoutId() = R.layout.fragment_image

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = context as MainActivity
        val gridManager = GridLayoutManager(activity,3)
        rvMedia.layoutManager = gridManager
        adapter = BaseAdapter(LayoutInflater.from(context),R.layout.item_image)
       // val adapter = ImageAdapter()
        adapter.listener = this
        rvMedia.adapter = adapter
        viewModel = ViewModelProviders.of(activity)[AppViewModel::class.java]
        viewModel.listImage.observe(viewLifecycleOwner, Observer {
            //adapter.listData = it
            adapter.data = it
        })
    }

    override fun onMediaClick(image: Image) {
        val file = File(image.path)
        if (!file.exists())
            return
        if (file.extension.toLowerCase(Locale.getDefault())=="gif"){
            Toast.makeText(activity, "Gif image", Toast.LENGTH_SHORT).show()
            WallpaperAdapter.setWallpaperByGif(activity,image.path,MainActivity.REQUEST_SETLIVE)
        }else{
            Toast.makeText(activity, "Normal image", Toast.LENGTH_SHORT).show()
            val dialog = WallpaperDialog(activity)
            dialog.onSetBoth = {
                WallpaperAdapter.setWallpaper(WallpaperAdapter.Key.SET_BOTH,image.path,{
                    activity.showLoading()
                },{ activity.showLoading(false)})
            }
            dialog.onSetHome = {
                WallpaperAdapter.setWallpaper(WallpaperAdapter.Key.SET_AS_HOME,image.path,{
                    activity.showLoading()
                },{ activity.showLoading(false)})
            }
            dialog.onSetLock = {
                WallpaperAdapter.setWallpaper(WallpaperAdapter.Key.SET_AS_LOCK,image.path,{
                    activity.showLoading()
                },{ activity.showLoading(false)})
            }
            dialog.show()
        }
    }
}