package com.example.mailapp.ui.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mailapp.R
import com.example.mailapp.databinding.FragmentBottomMenuBinding
import com.example.mailapp.viewmodels.MainMenuViewModel

class BottomMenuFragment: BaseFragment<FragmentBottomMenuBinding, MainMenuViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_bottom_menu
    override val viewModel: MainMenuViewModel by activityViewModels()

    override fun initData() {

    }

    override fun initView(view: View) {
        setListener()
        viewModel.setDefaultTab()
    }

    private fun setListener(){
        vd.bottomNavigationView.setOnItemSelectedListener {
            viewModel.clickTab(it.itemId)
            true // 항목을 선택한 항목으로 표시하려면 true, 항목을 선택하지 않아야 하는 경우 false
        }
    }

    override fun bind() {
        viewModel.selectTabId.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Log.d("TAG", "bottomNavigationView click debug selectTab observe => id[$it]")
                vd.bottomNavigationView.selectedItemId = it // 잘못된 id값이면 아무 일도 일어나지 않는다 => 내부에서 findItem 을 실시
            }
        }
    }
}