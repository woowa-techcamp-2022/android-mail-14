package com.example.mailapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mailapp.R
import com.example.mailapp.model.SingleEvent

class MainMenuViewModel: BaseViewModel() {
    private val _selectTabId: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    val selectTabId: LiveData<SingleEvent<Int>> = _selectTabId

    private val _showMailList: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val showMailList: LiveData<SingleEvent<Unit>> = _showMailList

    private val _showSetting: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val showSetting: LiveData<SingleEvent<Unit>> = _showSetting

    /**
     * data
     */
    private var currentSelectTabId: Int? = null

    /**
     * event from view
     */
    fun setDefaultTab(){
        _selectTabId.value = SingleEvent(currentSelectTabId ?: -1)
    }

    fun setSelectTab(id: Int) {
        _selectTabId.value = SingleEvent(id)
    }

    fun clickTab(id: Int){
        Log.d("TAG", "bottomNavigationView click debug click tab at view model => id[$id]")
        currentSelectTabId = id
        when(id){
            R.id.menu_mail -> {
                _showMailList.value = SingleEvent(Unit)
            }
            R.id.menu_setting -> {
                _showSetting.value = SingleEvent(Unit)
            }
        }
    }
}