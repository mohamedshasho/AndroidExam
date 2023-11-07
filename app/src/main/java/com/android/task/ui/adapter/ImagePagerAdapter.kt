package com.android.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.task.databinding.SliderImageItemBinding
import com.android.task.data.source.network.model.ImageSlider

class ImagePagerAdapter(private val items: List<ImageSlider>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            SliderImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(view: ImageViewHolder, position: Int) {
        val holder = view.holder
        val item = items[position]
        holder.imageView.setImageResource(item.imageId)
    }

    override fun getItemCount() = items.size


    class ImageViewHolder(val holder: SliderImageItemBinding) :
        RecyclerView.ViewHolder(holder.root)
}