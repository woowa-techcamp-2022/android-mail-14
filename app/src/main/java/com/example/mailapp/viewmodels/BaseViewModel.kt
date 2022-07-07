package com.example.mailapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    /**
     * event
     */
    private val _showToastEvent: MutableLiveData<String> = MutableLiveData()
    val showToastEvent: LiveData<String> = _showToastEvent
    protected fun showToast(message: String) = _showToastEvent.postValue(message)
}