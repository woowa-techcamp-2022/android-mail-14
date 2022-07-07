package com.example.mailapp

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mailapp.databinding.ActivityMainBinding
import com.example.mailapp.util.getWindowWidthDp
import com.example.mailapp.view.BaseActivity

/**
 * fragment 의 tag 정보는 view model 에 type 과 함께 enum 으로 지장하고,
 * event 를 통해 enum 을 내려보내준다
 *
 * enum 값에 따라서 fragment 표시 -> 해당 event 보내기 위한 event 는 width 정보와 가로, 세로 정보를 함께 보낸다
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        fun get(context: Context?, nickname: String, email: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("EXTRA_NICKNAME", nickname)
                putExtra("EXTRA_EMAIL", email)
            }
        }
    }
    override val vd: ActivityMainBinding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }
    override val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(vd.toolbar)
        initNavigationView()
        setFragmentAccordingToDevice()
    }

    private fun setFragmentAccordingToDevice(){
        val fragmentInfo = getFragmentAccordingToDevice()
        supportFragmentManager.beginTransaction().let {
            if(supportFragmentManager.fragments.isEmpty())
                it.add(R.id.containerFragment, fragmentInfo.first, fragmentInfo.second).commit()
            else
                it.replace(R.id.containerFragment, fragmentInfo.first, fragmentInfo.second).commit()
        }
    }

    private fun getFragmentAtTransaction(tag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag)
    }
    private fun getFragmentAccordingToDevice(): Pair<Fragment, String>{
        val width = getWindowWidthDp()
        val bottomNaviFragmentTag = "bottom_navi_fragment"
        val railNaviFragmentTag = "rail_navi_fragment"
        val bottomNaviFragment = (getFragmentAtTransaction(bottomNaviFragmentTag) ?: BottomMenuFragment()) to bottomNaviFragmentTag
        val railNaviFragment = (getFragmentAtTransaction(railNaviFragmentTag) ?: RailMenuFragment()) to railNaviFragmentTag
        val fragmentInfo: Pair<Fragment, String> = when(resources.configuration.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                Log.d("TAG", "orientation changed[ORIENTATION_LANDSCAPE][$width]")
                if(width >= 600){
                    railNaviFragment
                }else{
                    bottomNaviFragment
                }
            }
            // 태블릿의 경우 가로가 더 길때 세로모드일지, 가로모드일지 구분이 애매해서 일단 세로모드일때도 width 체크 코드 추가
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.d("TAG", "orientation changed[ORIENTATION_PORTRAIT][$width]")
                if(width >= 600){
                    railNaviFragment
                }else{
                    bottomNaviFragment
                }
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                Log.d("TAG", "orientation changed[ORIENTATION_UNDEFINED]")
                bottomNaviFragment
            }
            else -> bottomNaviFragment
        }
        return fragmentInfo
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
                it.isChecked = true
                vd.drawerLayout.closeDrawers()
                when(it.itemId){
                    R.id.menu_main_navigation_view_item_primary -> {
                        showToast("click primary")
                    }
                    R.id.menu_main_navigation_view_item_social -> {
                        showToast("click social")
                    }
                    R.id.menu_main_navigation_view_item_promotions -> {
                        showToast("click promotions")
                    }
                }
                true
            }
            setCheckedItem(R.id.menu_main_navigation_view_item_primary)  // default select item
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                vd.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun bind(savedInstanceState: Bundle?) {

    }
}