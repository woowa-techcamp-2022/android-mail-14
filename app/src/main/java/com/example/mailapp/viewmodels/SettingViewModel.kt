package com.example.mailapp.viewmodels

import androidx.lifecycle.MutableLiveData

class SettingViewModel: BaseViewModel() {
    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = _nickname

    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = _email

    fun initView(nickname: String?, email: String?){
        if(nickname.isNullOrBlank() || email.isNullOrBlank()){
            finisView("가입 정보가 없습니다")
            return
        }
        _nickname.value = nickname
        _email.value = email
    }
}