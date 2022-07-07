package com.example.mailapp.ui.fragments

import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.example.mailapp.R
import com.example.mailapp.databinding.FragmentRailMenuBinding
import com.example.mailapp.viewmodels.MainMenuViewModel

class RailMenuFragment: BaseFragment<FragmentRailMenuBinding, MainMenuViewModel>() {
    override val layoutResId: Int
        get() = R.layout.fragment_rail_menu
    override val viewModel: MainMenuViewModel by activityViewModels()

    override fun initData() {

    }

    override fun initView(view: View) {
        setListener()
        viewModel.setDefaultTab()
    }

    private fun setListener(){
        vd.railNavigationView.setOnItemSelectedListener {
            viewModel.clickTab(it.itemId)
            true // 항목을 선택한 항목으로 표시하려면 true, 항목을 선택하지 않아야 하는 경우 false
        }
    }

    override fun bind() {
        viewModel.selectTabId.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Log.d("TAG", "bottomNavigationView click debug selectTab observe => id[$it]")
                if(it<0){
                    vd.railNavigationView.selectedItemId = vd.railNavigationView.menu[0].itemId
                    return@observe
                }
                vd.railNavigationView.selectedItemId = it // 잘못된 id 값이면 아무 일도 일어나지 않는다 => 내부에서 findItem 을 실시
            }
        }

        viewModel.showMailList.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                childFragmentManager.beginTransaction().replace(R.id.containerFragmentRailMenu, MailListFragment()).commit()
            }
        }

        viewModel.showSetting.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                childFragmentManager.beginTransaction().replace(
                    R.id.containerFragmentRailMenu,
                    SettingFragment.get(
                        arguments?.getString(SettingFragment.nicknameExtraKey),
                        arguments?.getString(SettingFragment.emailExtraKey)
                    )
                ).commit()
            }
        }
    }
}