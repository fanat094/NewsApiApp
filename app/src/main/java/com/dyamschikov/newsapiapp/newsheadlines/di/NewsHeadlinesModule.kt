package com.dyamschikov.newsapiapp.newsheadlines.di

import androidx.lifecycle.ViewModel
import com.dyamschikov.newsapiapp.di.factory.ViewModelKey
import com.dyamschikov.newsapiapp.newsheadlines.presentation.viewmodel.NewsHeadlinesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**Binding into map*/
@Suppress("unused")
@Module
abstract class NewsHeadlinesModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsHeadlinesViewModel::class)
    abstract fun bindViewModel(viewModel: NewsHeadlinesViewModel): ViewModel
}