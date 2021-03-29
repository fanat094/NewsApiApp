package com.dyamschikov.newsapiapp.di.component

import android.content.Context
import com.dyamschikov.newsapiapp.di.factory.ViewModelBuilderModule
import com.dyamschikov.newsapiapp.di.module.AppModule
import com.dyamschikov.newsapiapp.di.module.AppSubcomponents
import com.dyamschikov.newsapiapp.di.module.LocalModule
import com.dyamschikov.newsapiapp.di.module.RepositoryModule
import com.dyamschikov.newsapiapp.newsdetailsnews.di.NewsDetailsComponent
import com.dyamschikov.newsapiapp.newseverything.di.NewsEverythingComponent
import com.dyamschikov.newsapiapp.newsheadlines.di.NewsHeadlinesComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        LocalModule::class,
        AppSubcomponents::class,
        ViewModelBuilderModule::class,
        RepositoryModule::class]
)
interface AppComponent {
    /**Factory to create instances of the AppComponent*/
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    /** Expose NewsComponent factory from the graph*/
    fun newsHeadlinesComponent(): NewsHeadlinesComponent.Factory
    fun newsEverythingComponent(): NewsEverythingComponent.Factory
    fun newsDetailsComponent(): NewsDetailsComponent.Factory
}