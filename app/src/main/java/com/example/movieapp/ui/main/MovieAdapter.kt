package com.example.movieapp.ui.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.ui.MovieViewHolder
import com.example.movieapp.ui.SeparatorViewHolder

class MovieAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.movie_view_item)
            MovieViewHolder.create(parent) else
            SeparatorViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
       uiModel?.let {uiModel ->  when(uiModel){
           is UiModel.MovieItem -> (holder as MovieViewHolder).bind(uiModel.movie, uiModel.onPageClicked)
           is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.year.toString())
       }
       }
    }
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.MovieItem -> R.layout.movie_view_item
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                (oldItem is UiModel.MovieItem && newItem is UiModel.MovieItem &&
                        oldItem.movie.title == newItem.movie.title) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.year == newItem.year)

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }
}