package com.example.mailapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.mailapp.viewmodels.BaseViewModel

abstract class BaseFragment<T: ViewDataBinding, R: BaseViewModel>: Fragment() {

    abstract val layoutResId: Int
    abstract val viewModel: R

    protected lateinit var vd: T
    protected var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vd = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutResId, container, false)
        initData()
        return vd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        bindAtBase()
        bind()
    }

    abstract fun initData()
    abstract fun initView(view: View)
    abstract fun bind()

    private fun bindAtBase(){

    }
}