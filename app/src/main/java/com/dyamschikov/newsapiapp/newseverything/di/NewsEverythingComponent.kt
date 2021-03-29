package com.dyamschikov.newsapiapp.newseverything.di

import com.dyamschikov.newsapiapp.newseverything.presentation.view.NewsEverythingFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewsEverythingModule::class])
interface NewsEverythingComponent {

    /**Factory to create instances of NewsComponent*/
    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsEverythingComponent
    }

    fun inject(fragment: NewsEverythingFragment)
}