package com.monica.nagwamediatask.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monica.nagwamediatask.di.factories.ViewModelFactory
import com.monica.nagwamediatask.di.keys.ViewModelKey
import com.monica.nagwamediatask.ui.MediaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MediaViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(MediaViewModel::class)
    abstract fun bindMediaViewModel(mediaViewModel: MediaViewModel): ViewModel
}