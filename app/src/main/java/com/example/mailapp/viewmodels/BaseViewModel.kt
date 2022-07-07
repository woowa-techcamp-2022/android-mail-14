package com.example.mailapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mailapp.model.SingleEvent

abstract class BaseViewModel: ViewModel() {
    /**
     * event
     */
    private val _showToastEvent: MutableLiveData<SingleEvent<String>> = MutableLiveData()
    val showToastEvent: LiveData<SingleEvent<String>> = _showToastEvent
    protected fun showToast(message: String) = _showToastEvent.postValue(SingleEvent(message))
}