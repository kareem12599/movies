package com.example.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.MovieViewItemBinding

class MovieViewHolder(private val binding: MovieViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie, onPageClicked: () -> Unit) {
        binding.apply {
            title.text = movie.title
            year.text = movie.year.toString()
            rating.text = movie.rating.toString()
        }
        binding.root.setOnClickListener {
            it.findNavController().navigate(R.id.detailsFragment)
            onPageClicked()
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_view_item, parent, false)
            val binding = MovieViewItemBinding.bind(view)
            return MovieViewHolder(binding)
        }
    }
}
