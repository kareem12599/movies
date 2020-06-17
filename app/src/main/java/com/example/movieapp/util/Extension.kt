package com.example.movieapp.util

import android.content.res.AssetManager
import android.view.View
import com.example.movieapp.MovieApplication
import com.example.movieapp.ui.details.DetailsFragment
import com.example.movieapp.ui.main.MainFragment

fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}

fun MainFragment.inject() = MovieApplication.appComponent(requireContext()).inject(this )

fun DetailsFragment.inject() = MovieApplication.appComponent(requireContext()).inject(this)

fun toVisibility(constraint: Boolean): Int = if (constraint) {
    View.VISIBLE
} else {
    View.GONE
}