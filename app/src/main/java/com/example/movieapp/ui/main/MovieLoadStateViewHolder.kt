package com.example.movieapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.MoviesLoadStateFooterViewItemBinding
import com.example.movieapp.util.toVisibility

class MovieLoadStateViewHolder (private val binding: MoviesLoadStateFooterViewItemBinding,
                                retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.visibility = toVisibility(loadState is LoadState.Loading)
        binding.errorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
        binding.retryButton.visibility = toVisibility(loadState !is LoadState.Loading)


    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movies_load_state_footer_view_item, parent, false)
            val binding = MoviesLoadStateFooterViewItemBinding.bind(view)
            return MovieLoadStateViewHolder(
                binding,
                retry
            )
        }
    }
}
