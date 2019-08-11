package com.umbo.skeleton.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umbo.skeleton.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    fun bindListViewModel(viewModel: ListViewModel): ViewModel
}