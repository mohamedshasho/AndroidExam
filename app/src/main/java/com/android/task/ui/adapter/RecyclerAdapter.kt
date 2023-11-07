package com.android.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.task.databinding.RecyclerItemBinding
import com.android.task.data.source.network.model.LabelItem

class RecyclerAdapter :
    ListAdapter<LabelItem, RecyclerAdapter.ImageViewHolder>(LabelComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(view: ImageViewHolder, position: Int) {
        val item = getItem(position)
        view.bind(item)
    }

    class ImageViewHolder(private val holder: RecyclerItemBinding) :
        RecyclerView.ViewHolder(holder.root) {
        fun bind(item: LabelItem) {
            holder.recyclerItemImage.setImageResource(item.image)
            holder.recyclerItemText.text = item.text
        }
    }



}


object LabelComparator : DiffUtil.ItemCallback<LabelItem>() {
    override fun areItemsTheSame(
        oldItem: LabelItem,
        newItem: LabelItem,
    ): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(
        oldItem: LabelItem,
        newItem: LabelItem,
    ): Boolean {
        return oldItem == newItem
    }
}