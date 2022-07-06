package com.example.mailapp

import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mailapp.databinding.FragmentMenuBottomNavigationBinding

class MenuBottomNavigationFragment: BaseFragment<FragmentMenuBottomNavigationBinding, MenuBottomNavigationViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_menu_bottom_navigation
    override val viewModel: MenuBottomNavigationViewModel by activityViewModels()

    override fun initData() {

    }

    override fun initView(view: View) {

    }

    override fun bind() {

    }
}