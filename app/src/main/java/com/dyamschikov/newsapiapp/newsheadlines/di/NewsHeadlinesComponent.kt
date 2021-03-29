package com.dyamschikov.newsapiapp.newsheadlines.di

import com.dyamschikov.newsapiapp.newsheadlines.presentation.view.NewsHeadlinesFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewsHeadlinesModule::class])
interface NewsHeadlinesComponent {

    /**Factory to create instances of NewsComponent*/
    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsHeadlinesComponent
    }

    fun inject(fragment: NewsHeadlinesFragment)
}