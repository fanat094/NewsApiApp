package com.dyamschikov.newsapiapp.newsheadlines.presentation.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.data.remote.model.asDatabaseModel
import com.dyamschikov.newsapiapp.domain.repository.NewsRepository
import com.dyamschikov.newsapiapp.newsheadlines.presentation.view.NewsHeadlinesFragment
import com.dyamschikov.newsapiapp.utils.Event
import com.dyamschikov.newsapiapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsHeadlinesViewModel @Inject constructor(private val repository: NewsRepository) :
    ViewModel() {

    val isLoading = ObservableBoolean()
    val isEmptyData = ObservableBoolean()

    private val _requestErrorEvent = MutableLiveData<Event<String>>()
    val requestErrorEvent: LiveData<Event<String>> get() = _requestErrorEvent

    private val _newsSuccess = MutableLiveData<List<ArticleUi>>()
    val newsSuccess: LiveData<List<ArticleUi>> get() = _newsSuccess

    private val _currentCountry = MutableLiveData<Int>()
    val currentCountry: LiveData<Int> get() = _currentCountry

    private val _requestOfflineEvent = MutableLiveData<Event<String>>()
    val requestOfflineEvent: LiveData<Event<String>> get() = _requestOfflineEvent

    init {
        val countryParam = NewsHeadlinesFragment.CountryCode.Ukraine.code
        fetchNews(countryParam)
    }

    fun fetchNews(countryParam: String) {
        isLoading.set(true)
        viewModelScope.launch(Dispatchers.Default) {
            val response = repository.fetchHeadlinesNews(countryParam)
            when (response.status) {
                Result.Status.SUCCESS -> {
                    val dataEntity = response.data?.asDatabaseModel()
                    dataEntity?.let { repository.setHeadlinesToStorage(it) }
                    val dataUi = repository.getHeadlineFromStorage()
                    _newsSuccess.postValue(dataUi)
                }
                Result.Status.ERROR -> {
                    val dataUi = repository.getHeadlineFromStorage()
                    if (dataUi.isNullOrEmpty()) {
                        _requestErrorEvent.postValue(
                            Event(
                                response.message ?: LABEL_UNEXPECTED_ERROR
                            )
                        )
                        isEmptyData.set(true)
                    } else {
                        _newsSuccess.postValue(dataUi)
                        _requestOfflineEvent.postValue(Event(LABEL_OFFLINE))
                    }
                }
            }
            isLoading.set(false)
        }
    }

    fun setCurrentCountry(currentCountry: Int) {
        _currentCountry.postValue(currentCountry)
    }

    companion object {
        const val LABEL_OFFLINE = "Offline"
        const val LABEL_UNEXPECTED_ERROR = "Unexpected Error!"
    }
}