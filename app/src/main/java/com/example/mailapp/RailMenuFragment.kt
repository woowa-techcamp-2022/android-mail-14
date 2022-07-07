package com.example.mailapp

import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mailapp.databinding.FragmentRailMenuBinding

class RailMenuFragment: BaseFragment<FragmentRailMenuBinding, MainMenuViewModel>() {
    override val layoutResId: Int
        get() = R.layout.fragment_rail_menu
    override val viewModel: MainMenuViewModel by activityViewModels()

    override fun initData() {

    }

    override fun initView(view: View) {

    }

    override fun bind() {

    }
}