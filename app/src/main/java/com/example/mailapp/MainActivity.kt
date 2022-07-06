package com.example.mailapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
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

    }

    override fun bind(savedInstanceState: Bundle?) {

    }
}