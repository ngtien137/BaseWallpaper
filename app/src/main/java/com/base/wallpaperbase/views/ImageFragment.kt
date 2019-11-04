package com.base.wallpaperbase.views

import android.os.Bundle
import android.view.LayoutInflater
import com.base.baselibrary.adapter.BaseAdapter
import com.base.baselibrary.fragment.BaseTitleFragment
import com.base.wallpaperbase.R
import com.base.wallpaperbase.databinding.FragmentImageBinding
import com.base.wallpaperbase.model.Media
import com.base.wallpaperbase.model.MediaListener
import com.base.wallpaperbase.model.image.Image

class ImageFragment : BaseTitleFragment<FragmentImageBinding>(),MediaListener {

    private lateinit var adapter:BaseAdapter<Image>

    override fun getTitle() = "Images"

    override fun getLayoutId() = R.layout.fragment_image

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = BaseAdapter(LayoutInflater.from(context),R.layout.item_media)
        adapter.listener = this
    }

    override fun onMediaClick(media: Media) {
        if (media is Image){

        }
    }
}