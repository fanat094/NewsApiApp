package com.dyamschikov.newsapiapp.newsdetailsnews.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor() :
    ViewModel() {
    private val _articlePosition = MutableLiveData<Int>()
    val articlePosition: LiveData<Int> get() = _articlePosition

    fun setCurrentItemVp(index:Int){
        _articlePosition.postValue(index)
    }
}
