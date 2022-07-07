package com.example.mailapp.ui.activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.mailapp.showToast
import com.example.mailapp.viewmodels.BaseViewModel

abstract class BaseActivity<T: ViewDataBinding, R: BaseViewModel>: AppCompatActivity() {

    abstract val vd: T
    abstract val viewModel: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vd.root)
        initData(savedInstanceState)
        initView(savedInstanceState)
        bindAtBase()
        bind(savedInstanceState)
    }

    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun bind(savedInstanceState: Bundle?)

    private fun bindAtBase(){
        viewModel.showToastEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                showToast(it)
            }
        }
    }

    protected fun setKeyboardShown(shown: Boolean, view: View){
        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        if(shown) // flag 0은 추가동작이 없음을 알림
            imm.showSoftInput(view, 0)
        else
            imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun setInputAdjustMode(adjustResize: Boolean){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(adjustResize.not()) // false is SOFT_INPUT_ADJUST_RESIZE
        } else {
            if(adjustResize)
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            else
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }
}