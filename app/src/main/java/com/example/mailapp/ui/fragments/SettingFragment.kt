package com.example.mailapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mailapp.R
import com.example.mailapp.databinding.FragmentSettingBinding
import com.example.mailapp.util.showToast
import com.example.mailapp.viewmodels.SettingViewModel

class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    companion object {
        const val nicknameExtraKey = "EXTRA_NICKNAME"
        const val emailExtraKey = "EXTRA_EMAIL"
        fun get(nickname: String?, email: String?): SettingFragment{
            return SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(nicknameExtraKey, nickname)
                    putString(emailExtraKey, email)
                }
            }
        }
    }
    override val layoutResId: Int
        get() = R.layout.fragment_setting
    override val viewModel: SettingViewModel by activityViewModels()

    override fun initData() {

    }

    override fun initView(view: View) {
        viewModel.initView(arguments?.getString(nicknameExtraKey), arguments?.getString(emailExtraKey))
    }

    override fun bind() {
        viewModel.finishViewEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                showToast(it)
            }
        }

        viewModel.nickname.observe(this){
            vd.tvNickname.text = it
        }

        viewModel.email.observe(this){
            vd.tvEmail.text = it
        }
    }
}