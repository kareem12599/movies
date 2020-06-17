package com.example.movieapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.util.MoviesViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: MoviesViewModelFactory): ViewModelProvider.Factory
}