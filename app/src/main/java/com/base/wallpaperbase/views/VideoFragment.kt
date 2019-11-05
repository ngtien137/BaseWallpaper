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
import com.base.wallpaperbase.adapter.VideoAdapter
import com.base.wallpaperbase.databinding.FragmentVideoBinding
import com.base.wallpaperbase.model.video.VideoListener
import com.base.wallpaperbase.model.video.Video
import com.base.wallpaperbase.service.VideoLiveWallpaper
import com.base.wallpaperbase.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : BaseTitleFragment<FragmentVideoBinding>(),
    VideoListener {

    private lateinit var adapter:VideoAdapter//BaseAdapter<Video>
    private lateinit var viewModel:AppViewModel
    private lateinit var activity:MainActivity
    private val TAG = "NVT"+javaClass.simpleName

    override fun getTitle() = "Video"

    override fun getLayoutId() = R.layout.fragment_image

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = context as MainActivity
        val gridManager = GridLayoutManager(activity,3)
        rvMedia.layoutManager = gridManager
        adapter = VideoAdapter()
        adapter.listener = this
        rvMedia.adapter = adapter
        viewModel = ViewModelProviders.of(activity)[AppViewModel::class.java]
        viewModel.listVideo.observe(viewLifecycleOwner, Observer {
            adapter.listData = it
        })
    }

    override fun onMediaClick(video: Video) {
        Toast.makeText(activity, "Set ${video.displayName} to wallpaper", Toast.LENGTH_SHORT).show()
        VideoLiveWallpaper.setToWallPaper(activity,video.path)
    }
}