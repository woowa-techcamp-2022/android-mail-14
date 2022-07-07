package com.example.mailapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mailapp.R

class MainMenuViewModel: BaseViewModel() {
    private val _selectTabId: MutableLiveData<Int> = MutableLiveData()
    val selectTabId: LiveData<Int> = _selectTabId

    /**
     * data
     */
    private var currentSelectTabId: Int? = null

    /**
     * event from view
     */
    fun setDefaultTab(){
        _selectTabId.value = currentSelectTabId ?: 0
    }

    fun clickTab(id: Int){
        Log.d("TAG", "bottomNavigationView click debug click tab at view model => id[$id]")
        currentSelectTabId = id
        when(id){
            R.id.menu_mail -> {
                showToast("mail tab")
            }
            R.id.menu_setting -> {
                showToast("setting tab")
            }
        }
    }
}