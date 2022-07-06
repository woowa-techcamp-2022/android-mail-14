package com.example.mailapp.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.mailapp.view.BaseActivity
import com.example.mailapp.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val vd: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(LayoutInflater.from(this)) }
    override val viewModel: LoginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        Log.d("TAG","rotate debug => onCreate[$savedInstanceState]")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("TAG","rotate debug => onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("TAG","rotate debug => onRestoreInstanceState")
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        setListener()
        if(savedInstanceState != null){
            viewModel.restoreInputData()
        }
    }
    private fun setListener(){
        vd.background.setOnClickListener {
            viewModel.backgroundClick()
        }

        vd.editTextWithRuleNickname.apply {
            tagEditText = "nickname"
            setRules(
                viewModel.nicknameInputHint,
                viewModel.nicknameInputRuleErrorMessage,
                viewModel.nicknameInputRule
            )
            setTextChangedWithCorrectRule(viewModel.nicknameInputCorrectRule)
        }

        vd.editTextWithRuleEmail.apply {
            tagEditText = "email"
            setRules(
                viewModel.emailInputHint,
                viewModel.emailInputRuleErrorMessage,
                viewModel.emailInputRule
            )
            setTextChangedWithCorrectRule(viewModel.emailInputCorrectRule)
        }

        vd.btnNext.setOnClickListener {
            Log.d("TAG","focus debug [$currentFocus], ${currentFocus?.tag}")
            viewModel.nextButtonClick()
        }
    }

    override fun bind(savedInstanceState: Bundle?) {
        viewModel.keyboardShownEvent.observe(this){
            setKeyboardShown(it, vd.background)
        }

        viewModel.nextButtonEnableEvent.observe(this){
            vd.btnNext.isEnabled = it
        }

        viewModel.nicknameInput.observe(this){
            vd.editTextWithRuleNickname.setText(it)
        }

        viewModel.emailInput.observe(this){
            vd.editTextWithRuleEmail.setText(it)
        }
    }
}

/*
    edit text rotate 시 focus 문제
        // at onSaveInstanceState
        Log.d("TAG","rotate debug => onSaveInstanceState ==> [$currentFocus], ${currentFocus?.tag}")
        outState.putString("focus_view_tag", currentFocus?.tag.toString())
        // at onCreate or onRestoreInstanceState
        val tag = savedInstanceState.getString("focus_view_tag")
        Log.d("TAG","rotate debug => initView tag[$tag]")
        when(tag){
            vd.editTextWithRuleNickname.tagEditText -> {
                Log.d("TAG","rotate debug => nickname tag]")
                vd.editTextWithRuleNickname.setFocus(false)
                vd.editTextWithRuleNickname.setFocus(true)
            }
            vd.editTextWithRuleEmail.tagEditText -> {
                Log.d("TAG","rotate debug => nickname tag]")
                vd.editTextWithRuleNickname.setFocus(false)
                vd.editTextWithRuleEmail.setFocus(true)
            }
        }

 */