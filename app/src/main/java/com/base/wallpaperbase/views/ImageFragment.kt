package com.base.wallpaperbase.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.base.baselibrary.fragment.BaseTitleFragment
import com.base.wallpaperbase.MainActivity
import com.base.wallpaperbase.R
import com.base.wallpaperbase.adapter.ImageAdapter
import com.base.wallpaperbase.databinding.FragmentImageBinding
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.image.ImageListener
import com.base.wallpaperbase.service.GIFLiveWallpaper
import com.base.wallpaperbase.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File

class ImageFragment : BaseTitleFragment<FragmentImageBinding>(),
    ImageListener {

    //private lateinit var adapter:BaseAdapter<Image>
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
//        adapter = BaseAdapter(LayoutInflater.from(context),R.layout.item_image)
        val adapter = ImageAdapter()
        adapter.listener = this
        rvMedia.adapter = adapter
        viewModel = ViewModelProviders.of(activity)[AppViewModel::class.java]
        viewModel.listImage.observe(viewLifecycleOwner, Observer {
            adapter.listData = it
        })
    }

    override fun onMediaClick(image: Image) {
        val file = File(image.path)
        if (!file.exists())
            return
        if (file.extension.toLowerCase()=="gif"){
            Toast.makeText(activity, "Gif image", Toast.LENGTH_SHORT).show()
            GIFLiveWallpaper.setToWallPaper(activity,image.path)
        }else{
            Toast.makeText(activity, "Normal image", Toast.LENGTH_SHORT).show()
        }
    }
}