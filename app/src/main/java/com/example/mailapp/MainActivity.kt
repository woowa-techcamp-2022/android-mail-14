package com.example.mailapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mailapp.databinding.ActivityMainBinding
import com.example.mailapp.view.BaseActivity
import com.example.mailapp.view.BaseViewModel

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
    }

    private fun initNavigationView(){
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = "W Mail"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_navigation_view_button) // 뒤로가고 버튼 이미지 설정
        }

        vd.navigationView.setNavigationItemSelectedListener {
//            it.isChecked = true
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