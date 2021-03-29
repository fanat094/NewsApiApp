package com.dyamschikov.newsapiapp.di.module

import com.dyamschikov.newsapiapp.newsdetailsnews.di.NewsDetailsComponent
import com.dyamschikov.newsapiapp.newseverything.di.NewsEverythingComponent
import com.dyamschikov.newsapiapp.newsheadlines.di.NewsHeadlinesComponent
import dagger.Module

@Module(subcomponents = [NewsHeadlinesComponent::class, NewsEverythingComponent::class, NewsDetailsComponent::class])
class AppSubcomponents