package com.example.movieapp.di

import androidx.lifecycle.ViewModel
import com.example.movieapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module

import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
}
