package com.example.mailapp

import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mailapp.databinding.FragmentMenuNavigationRailBinding

class MenuRailNavigationViewFragment: BaseFragment<FragmentMenuNavigationRailBinding, MenuRailNavigationViewViewModel>() {
    override val layoutResId: Int
        get() = R.layout.fragment_menu_navigation_rail
    override val viewModel: MenuRailNavigationViewViewModel by activityViewModels()

    override fun initData() {

    }

    override fun initView(view: View) {

    }

    override fun bind() {

    }
}