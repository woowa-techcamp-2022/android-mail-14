package com.example.mailapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mailapp.model.MailModel
import com.example.mailapp.model.MailRepository
import com.example.mailapp.model.SingleEvent

class MailListViewModel: BaseViewModel() {
    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    val progress: LiveData<Boolean> = _progress

    private val _mailList: MutableLiveData<List<MailModel>> = MutableLiveData()
    val mailList: LiveData<List<MailModel>> = _mailList

    private val _mailTypeData: MutableLiveData<MailModel.MailType> = MutableLiveData()
    val mailTypeData: LiveData<MailModel.MailType> = _mailTypeData

    private val _changeMailTypeEvent: MutableLiveData<SingleEvent<MailModel.MailType>> = MutableLiveData()
    val changeMailTypeEvent: LiveData<SingleEvent<MailModel.MailType>> = _changeMailTypeEvent

    private val mailRepository = MailRepository()
    private var mailType: MailModel.MailType = MailModel.MailType.Primary
        set(value) {
            Log.d("TAG", "setter mail type : $value")
            _mailTypeData.value = value
            field = value
        }

    fun onBackPressed(){
        when(mailType){
            MailModel.MailType.Primary -> finisView()
            else -> {
                _changeMailTypeEvent.value = SingleEvent(MailModel.MailType.Primary)
            }
        }
    }

    fun changeMailType(type: MailModel.MailType){
        mailType = type
        fetchMailList()
    }
    fun fetchMailList(){
        _progress.value = true
        mailRepository.fetchMailList(mailType){
            _progress.value = false
            _mailList.value = it
        }
    }
}