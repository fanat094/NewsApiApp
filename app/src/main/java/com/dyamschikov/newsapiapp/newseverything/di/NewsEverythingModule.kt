package com.dyamschikov.newsapiapp.newseverything.di

import androidx.lifecycle.ViewModel
import com.dyamschikov.newsapiapp.di.factory.ViewModelKey
import com.dyamschikov.newsapiapp.newseverything.presentation.viewmodel.NewsEverythingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**Binding into map*/
@Suppress("unused")
@Module
abstract class NewsEverythingModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsEverythingViewModel::class)
    abstract fun bindViewModel(viewModel: NewsEverythingViewModel): ViewModel
}