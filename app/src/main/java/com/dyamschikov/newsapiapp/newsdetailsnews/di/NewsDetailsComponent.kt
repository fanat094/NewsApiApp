package com.dyamschikov.newsapiapp.newsdetailsnews.di

import com.dyamschikov.newsapiapp.newsdetailsnews.presentation.view.NewsDetailsFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewsDetailsModule::class])
interface NewsDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsDetailsComponent
    }

    fun inject(fragment: NewsDetailsFragment)
}