package com.example.mailapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.mailapp.*
import com.example.mailapp.databinding.ActivityMainBinding
import com.example.mailapp.model.MailModel
import com.example.mailapp.ui.fragments.*
import com.example.mailapp.util.getWindowWidthDp
import com.example.mailapp.viewmodels.MainViewModel

/**
 * fragment 의 tag 정보는 view model 에 type 과 함께 enum 으로 지장하고,
 * event 를 통해 enum 을 내려보내준다
 *
 * enum 값에 따라서 fragment 표시 -> 해당 event 보내기 위한 event 는 width 정보와 가로, 세로 정보를 함께 보낸다
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MailTypeSelectActivity {
    companion object {
        private const val nicknameExtraKey = "EXTRA_NICKNAME"
        private const val emailExtraKey = "EXTRA_EMAIL"
        fun get(context: Context?, nickname: String, email: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(nicknameExtraKey, nickname)
                putExtra(emailExtraKey, email)
            }
        }
    }
    override val vd: ActivityMainBinding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }
    override val viewModel: MainViewModel by viewModels()

    override val mailType: LiveData<MailModel.MailType>
        get() = viewModel.mailTypeSelect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        Log.d("TAG", "onBackPressed at main")
        when {
            vd.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                vd.drawerLayout.closeDrawers()
            }
            else -> super.onBackPressed()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(vd.toolbar)
        initNavigationView()
        viewModel.setFragmentAccordingToDevice(getWindowWidthDp(), resources.configuration.orientation)
    }

    private fun initNavigationView(){
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = "W Mail"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_navigation_view_button) // 뒤로가기 버튼 이미지 설정
        }

        vd.navigationView.apply {
            setNavigationItemSelectedListener {
                viewModel.clickDrawerMenu(it.itemId)
                true
            }
        }
        viewModel.clickDrawerMenu(viewModel.selectDrawerMenuId) // default select item
    }

    override fun bind(savedInstanceState: Bundle?) {
        viewModel.drawerMenuSelectEvent.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Log.d("TAG", "drawerMenuSelectEvent [$it]")
                vd.navigationView.setCheckedItem(it)
            }
        }

        viewModel.setDrawerShown.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                Log.d("TAG", "setDrawerShown event [$it]")
                if(vd.drawerLayout.isDrawerOpen(GravityCompat.START) == it)
                    return@observe
                if(it)
                    vd.drawerLayout.openDrawer(GravityCompat.START)
                else
                    vd.drawerLayout.closeDrawers()
            }
        }

        viewModel.showFragmentInfo.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                val fragment: Fragment = when(it){
                    MainViewModel.FragmentInfo.BottomMenu -> {
                        (getFragmentFromTransaction(it.tag) ?: BottomMenuFragment())
                    }
                    MainViewModel.FragmentInfo.RailMenu -> {
                        (getFragmentFromTransaction(it.tag) ?: RailMenuFragment())
                    }
                }
                applyFragment(fragment, it.tag)
            }
        }
    }

    private fun applyFragment(fragment: Fragment, tag: String){
        fragment.apply {
            arguments = Bundle().apply {
                Log.d("TAG", "extra data nick[${intent.getStringExtra(nicknameExtraKey)}], email[${intent.getStringExtra(emailExtraKey)}]")
                putString(nicknameExtraKey, intent.getStringExtra(nicknameExtraKey))
                putString(emailExtraKey, intent.getStringExtra(emailExtraKey))
            }
        }
        supportFragmentManager.beginTransaction().let {
            if(supportFragmentManager.fragments.isEmpty()) {
                it.add(R.id.containerFragmentMain, fragment, tag).commit()
            }
            else {
                it.replace(R.id.containerFragmentMain, fragment, tag).commit()
            }
        }
    }

    private fun getFragmentFromTransaction(tag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag)
    }

    override fun selectMailType(mailType: MailModel.MailType) {
        viewModel.selectMailType(mailType)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.clickOptionMenu(item.itemId)
        return super.onOptionsItemSelected(item)
    }
}