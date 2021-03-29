# NewsApiApp
An Android applicationto view news  implemented using the MVVM pattern, Retrofit2, Dagger2, LiveData, ViewModel, Coroutines, Room, Navigation Components, Data Binding and some other libraries from the Android Jetpack.
App fetches data from the News API to provide real time information.
## Preview
![alt text](https://github.com/fanat094/NewsApiApp/blob/master/raw/light_prev.png)
![alt text](https://github.com/fanat094/NewsApiApp/blob/master/raw/dark_prev.png)

## Download
Download latest APK [from here](https://github.com/fanat094/NewsApiApp/releases/download/v1.0/NewsApiApp.apk)
## Architecture
The architecture of this application relies and complies with the following points below:
- A single-activity architecture, using the [Navigation Components](https://developer.android.com/guide/navigation) to manage fragment operations.
- Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)(MVVM) which facilitates a separation of development of the graphical user interface.
- [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
## Technologies used:
-	[Android KTX](https://developer.android.com/kotlin/ktx) which helps to write more concise, idiomatic Kotlin code.
-	[Retrofit](https://square.github.io/retrofit/) a REST Client for Android which makes it relatively easy to retrieve and upload JSON via a REST based webservice.
-	[Dagger2](https://dagger.dev/) for dependency injection.
-	[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
-	[LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to handle data in a lifecycle-aware fashion.
-	[Navigation Component](https://developer.android.com/guide/navigation) to handle all navigations and also passing of data between destinations.
-	[Material Design](https://material.io/develop/android/docs/getting-started/) an adaptable system of guidelines, components, and tools that support the best practices of user interface design.
-	[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. writing to and reading from the database. Coroutines help in managing background threads and reduces the need for callbacks.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) an asynchronous data stream that sequentially emits values and completes normally or with an exception.
-	[Data Binding](https://developer.android.com/topic/libraries/data-binding/) to declaratively bind UI components in layouts to data sources.
-	[Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
-	[Paging Library](https://developer.android.com/topic/libraries/architecture/paging) helps you load and display small chunks of data at a time.
-	[Preferences](https://developer.android.com/guide/topics/ui/settings) to create interactive settings screens.
-	[ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) is an improved version of the ViewPager library that offers enhanced functionality and addresses common difficulties with using ViewPager.
- [Glide](https://bumptech.github.io/glide/) is a fast and efficient image loading library for Android focused on smooth scrolling.
