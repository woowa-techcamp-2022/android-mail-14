package com.example.mailapp

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

class LoginViewModel: BaseViewModel() {
    /**
     * data event
     */
    private val _nicknameInput: MutableLiveData<String> = MutableLiveData()
    val nicknameInput: LiveData<String> = _nicknameInput

    private val _emailInput: MutableLiveData<String> = MutableLiveData()
    val emailInput: LiveData<String> = _emailInput

    /**
     * event
     */
    private val _keyboardShownEvent: MutableLiveData<Boolean> = MutableLiveData()
    val keyboardShownEvent: LiveData<Boolean> = _keyboardShownEvent

    private val _nextButtonEnableEvent: MutableLiveData<Boolean> = MutableLiveData()
    val nextButtonEnableEvent: LiveData<Boolean> = _nextButtonEnableEvent

    /**
     * data
     */
    private var inputNickname: String? = null
    private var inputEmail: String? = null
    private var inputNicknameCorrect = false
    private var inputEmailCorrect = false

    val nicknameInputHint: String = "닉네임"
    val nicknameInputRuleErrorMessage: String = "닉네임은 영문과 숫자를 결합한 4~12자로 입력해주세요"
    val nicknameInputRule: (String)->(Boolean) = {
        inputNickname = it
        val nicknamePattern = Pattern.compile("^[a-zA-Z0-9]+$") // 숫자나 영단어로만 이루어 진 경우 걸러내도록 수정 필요
        inputNicknameCorrect = it.length in 4..12 && nicknamePattern.matcher(it).matches()
        _nextButtonEnableEvent.value = inputNicknameCorrect && inputEmailCorrect
        inputNicknameCorrect
    }

    val nicknameInputCorrectRule: (String)->(Unit) = {
        inputNickname = it
    }

    val emailInputHint: String = "이메일"
    val emailInputRuleErrorMessage: String = "이메일 형식이 올바르지 않습니다"
    val emailInputRule: (String)->(Boolean) = {
        inputEmail = it
        inputEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(it).matches()
        _nextButtonEnableEvent.value = inputNicknameCorrect && inputEmailCorrect
        inputEmailCorrect
    }

    val emailInputCorrectRule: (String)->(Unit) = {
        inputEmail = it
    }

    /**
     * event from view
     */
    fun restoreInputData(){
        _nicknameInput.value = inputNickname
        _emailInput.value = inputEmail
    }

    fun backgroundClick(){
        _keyboardShownEvent.value = false
    }

    fun nextButtonClick(){
        
    }

}