package com.example.mailapp.ui.fragments

import android.content.Context
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.mailapp.R
import com.example.mailapp.databinding.FragmentRailMenuBinding
import com.example.mailapp.viewmodels.MainMenuViewModel

class RailMenuFragment: BaseFragment<FragmentRailMenuBinding, MainMenuViewModel>() {
    override val layoutResId: Int
        get() = R.layout.fragment_rail_menu
    override val viewModel: MainMenuViewModel by activityViewModels()

    private val onBackPressedCallback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            Log.d("TAG", "onBackPressed at bottom menu")
            Log.d("TAG", "backStack size : ${childFragmentManager.backStackEntryCount}")
            if(childFragmentManager.backStackEntryCount != 0) {
                childFragmentManager.popBackStack()
            }else
                requireActivity().finish()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

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

        childFragmentManager.addOnBackStackChangedListener { // after add/push/pop
            viewModel.backStackChanged(childFragmentManager.backStackEntryCount, childFragmentManager.fragments.firstOrNull()?.tag)
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

        viewModel.checkTabIdx.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if(it in 0 until vd.railNavigationView.menu.size)
                    vd.railNavigationView.menu[it].isChecked = true
            }
        }

        viewModel.showMailList.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                //모든 backStack 을 제거한다 -> mail 에서 뒤로가기를 누르면 앱이 종료되어야 한다
                childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                childFragmentManager.beginTransaction()
                    .replace(R.id.containerFragmentRailMenu, MailListFragment(), it)
                    .commit()
            }
        }

        viewModel.showSetting.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                replaceSettingFragment(it)
            }
        }
    }

    private fun replaceSettingFragment(tag: String){
        Log.d("TAG", "extra data bundle nick[${arguments?.getString(SettingFragment.nicknameExtraKey)}] email[${arguments?.getString(SettingFragment.emailExtraKey)}]")
        //setting 에서 뒤로가기를 누르면 mail 로 이동해야하니, 기존에 setting tag 의 backStack 과, 그 위의 backStack 들을 제거하고, add backStack 해준다
        childFragmentManager.popBackStack(SettingFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        childFragmentManager.beginTransaction()
            .replace(
                R.id.containerFragmentRailMenu,
                SettingFragment.get(
                    arguments?.getString(SettingFragment.nicknameExtraKey),
                    arguments?.getString(SettingFragment.emailExtraKey)
                ),
                tag
            )
            .addToBackStack(SettingFragment.TAG) // 현재 시점의 transaction 상태를 backStack 에 저장
            .commit()
    }
}