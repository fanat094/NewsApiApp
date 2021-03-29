package com.dyamschikov.newsapiapp.newsdetailsnews.di

import androidx.lifecycle.ViewModel
import com.dyamschikov.newsapiapp.di.factory.ViewModelKey
import com.dyamschikov.newsapiapp.newsdetailsnews.presentation.viewmodel.NewsDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**Binding into map*/
@Suppress("unused")
@Module
abstract class NewsDetailsModule{

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    abstract fun bindViewModel(viewModel: NewsDetailsViewModel): ViewModel
}