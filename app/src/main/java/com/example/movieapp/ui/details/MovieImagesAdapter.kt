package com.example.movieapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.remote.PicData
import com.example.movieapp.databinding.PicViewItemBinding

class MovieImagesAdapter : ListAdapter<PicData, PicViewHolder>(
    PICS_COMPERATOR
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {
        return PicViewHolder.creat(
            parent
        )
    }

    override fun onBindViewHolder(holder: PicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val PICS_COMPERATOR = object : DiffUtil.ItemCallback<PicData>() {
             override fun areItemsTheSame(oldItem: PicData, newItem: PicData) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PicData, newItem: PicData) = oldItem == newItem
        }
    }
}

class PicViewHolder(private val binding: PicViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(picData: PicData) {

    }

    companion object {
        fun creat(parent: ViewGroup): PicViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.pic_view_item, parent, false)
            val binding = PicViewItemBinding.bind(view)
            return PicViewHolder(binding)
        }
    }
}
