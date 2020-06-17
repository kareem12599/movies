package com.example.movieapp.di.core

import android.content.Context
import com.example.movieapp.di.MainViewModelModule
import com.example.movieapp.di.ViewModelFactoryModule
import com.example.movieapp.ui.details.DetailsFragment
import com.example.movieapp.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        ViewModelFactoryModule::class,
        MainViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent

    }
    fun inject(detailsFragment: DetailsFragment)
    fun inject(mainFragment: MainFragment)


}



