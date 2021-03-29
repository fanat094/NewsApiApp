package com.dyamschikov.newsapiapp.newseverything.presentation.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.data.remote.model.asDataUi
import com.dyamschikov.newsapiapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsEverythingViewModel @Inject constructor(private val repository: NewsRepository) :
    ViewModel() {

    private val _newsEverythingSuccess = MutableLiveData<PagingData<ArticleUi>>()
    val newsEverythingSuccess: LiveData<PagingData<ArticleUi>> get() = _newsEverythingSuccess
    val isEmptyData = ObservableBoolean(false)

    init {
        initPagingFlow()
    }

    private fun initPagingFlow() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.fetchEverythingNews()
                .cachedIn(viewModelScope)
                .collectLatest { article ->
                    val data = article.map { it.asDataUi() }
                    _newsEverythingSuccess.postValue(data)
                }
        }
    }
}