package com.base.wallpaperbase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.base.baselibrary.adapter.BaseAdapter
import com.base.wallpaperbase.R
import com.base.wallpaperbase.databinding.ItemImageBinding
import com.base.wallpaperbase.model.image.Image
import com.base.wallpaperbase.model.image.ImageListener
import kotlinx.android.synthetic.main.item_image.view.*
import java.io.File

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    var listData = ArrayList<Image>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: BaseAdapter.ListItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = listData[holder.adapterPosition]
        holder.binding.image = image
        holder.binding.root.setOnClickListener {
            listener?.let {
                (listener as ImageListener).onMediaClick(image)
            }
        }
        holder.binding.root.tvType.text = File(image.path).extension
    }

    class ImageHolder(var binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}