package com.base.wallpaperbase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.base.baselibrary.adapter.BaseAdapter
import com.base.wallpaperbase.R
import com.base.wallpaperbase.databinding.ItemVideoBinding
import com.base.wallpaperbase.model.video.Video
import com.base.wallpaperbase.model.video.VideoListener

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {

    var listData = ArrayList<Video>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: BaseAdapter.ListItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_video,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val video = listData[holder.adapterPosition]
        holder.binding.video = video
        holder.binding.root.setOnClickListener {
            listener?.let {
                (listener as VideoListener).onMediaClick(video)
            }

        }
    }

    class VideoHolder(var binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)
}