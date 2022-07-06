package com.example.mailapp.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mailapp.view.BaseViewModel
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

    private val _successLoginEvent: MutableLiveData<Pair<String, String>> = MutableLiveData()
    val successLoginEvent: MutableLiveData<Pair<String, String>> = _successLoginEvent

    /**
     * data
     */
    private var inputNickname: String? = null
    private var inputEmail: String? = null
    private var inputNicknameCorrect = false
    private var inputEmailCorrect = false


    // 정규식 테스트 사이트 https://regex101.com/r/cO8lqs/11
    val nicknameInputHint: String = "닉네임"
    val nicknameInputRuleErrorMessage: String = "닉네임은 영문과 숫자를 결합한 4~12자로 입력해주세요"
    val nicknameInputRule: (String)->(Boolean) = {
        inputNickname = it
        val nicknamePattern = Pattern.compile("^[a-zA-Z0-9]+$")
        Log.d("TAG", "nickname[$it] contain char => ${it.contains(Regex("^[a-z]"))}")
        Log.d("TAG", "nickname[$it] contain num => ${it.contains(Regex("[0-9]"))}")
        inputNicknameCorrect = it.length in 4..12 &&
                nicknamePattern.matcher(it).matches() && // 숫자나 영단어만 허용 -> 모두 숫자나 모두 영단어로 이루어진 경우 포함
                it.contains(Regex("^[a-z]")) && // 영단어가 포함되어있는지 
                it.contains(Regex("[0-9]"))  // 숫자가 포함되어있는지
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
        if(inputNickname.isNullOrBlank() || inputEmail.isNullOrBlank()){
            showToast("입력값이 올바르지 않습니다")
            return
        }
        _successLoginEvent.value = inputNickname!! to inputEmail!!
    }

}