package com.base.wallpaperbase.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.base.baselibrary.adapter.BaseAdapter
import com.base.baselibrary.fragment.BaseTitleFragment
import com.base.wallpaperbase.MainActivity
import com.base.wallpaperbase.R
import com.base.wallpaperbase.databinding.FragmentResourceBinding
import com.base.wallpaperbase.model.resource.ObjectResource
import com.base.wallpaperbase.model.resource.ResourceListener
import com.base.wallpaperbase.utils.App
import com.base.wallpaperbase.utils.WallpaperAdapter
import com.base.wallpaperbase.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_image.*

class ObjectResourceFragment : BaseTitleFragment<FragmentResourceBinding>(),
    ResourceListener {

    private lateinit var adapter:BaseAdapter<ObjectResource>
    private lateinit var viewModel:AppViewModel
    private lateinit var activity:MainActivity
    private val TAG = "NVT"+javaClass.simpleName

    override fun getTitle() = "Resource"

    override fun getLayoutId() = R.layout.fragment_resource

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = context as MainActivity
        val gridManager = GridLayoutManager(activity,3)
        rvMedia.layoutManager = gridManager
        adapter = BaseAdapter(LayoutInflater.from(context),R.layout.item_resource)
       // val adapter = ImageAdapter()
        adapter.listener = this
        rvMedia.adapter = adapter
        viewModel = ViewModelProviders.of(activity)[AppViewModel::class.java]
        viewModel.listResource.observe(viewLifecycleOwner, Observer {
            //adapter.listData = it
            adapter.data = it
        })
    }

    override fun onResourceClick(resource: ObjectResource) {
        val fileName = resource.getName()
        Log.e(TAG,"Click: $resource")
        if (fileName.toLowerCase().contains("gif")){
            Toast.makeText(activity, "Setting Gif image as...", Toast.LENGTH_SHORT).show()
            WallpaperAdapter.setWallpaperByGif(activity,resource.id)
        }else if(fileName.toLowerCase().contains("video")) {
            Toast.makeText(activity, "Setting Video as...", Toast.LENGTH_SHORT).show()
            WallpaperAdapter.setWallpaperByVideo(activity,resource.id)
        }else{
            Toast.makeText(activity, "Setting Normal image as...", Toast.LENGTH_SHORT).show()
            val dialog = WallpaperDialog(activity)
            dialog.onSetBoth = {
                WallpaperAdapter.setWallpaper(WallpaperAdapter.Key.SET_BOTH,"",{
                    activity.showLoading()
                },{ activity.showLoading(false)},resource.id)
            }
            dialog.onSetHome = {
                WallpaperAdapter.setWallpaper(WallpaperAdapter.Key.SET_AS_HOME,"",{
                    activity.showLoading()
                },{ activity.showLoading(false)},resource.id)
            }
            dialog.onSetLock = {
                WallpaperAdapter.setWallpaper(WallpaperAdapter.Key.SET_AS_LOCK,"",{
                    activity.showLoading()
                },{ activity.showLoading(false)},resource.id)
            }
            dialog.show()
        }
    }
}