package com.example.mailapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mailapp.databinding.ActivityLoginBinding
import com.example.mailapp.viewmodels.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val vd: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(LayoutInflater.from(this)) }
    override val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        Log.d("TAG","rotate debug => onCreate[$savedInstanceState]")
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        setListener()
        viewModel.restoreInputData()
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
        }

        vd.editTextWithRuleEmail.apply {
            tagEditText = "email"
            setRules(
                viewModel.emailInputHint,
                viewModel.emailInputRuleErrorMessage,
                viewModel.emailInputRule
            )
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

        viewModel.nicknameInput.observe(this){
            vd.editTextWithRuleNickname.setText(it)
        }

        viewModel.emailInput.observe(this){
            vd.editTextWithRuleEmail.setText(it)
        }

        viewModel.nextButtonEnableEvent.observe(this){
            vd.btnNext.isEnabled = it
        }

        viewModel.successLoginEvent.observe(this){
            startActivity(MainActivity.get(this, it.first, it.second))
        }
    }
}

/*
    edit text rotate ??? focus ??????
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