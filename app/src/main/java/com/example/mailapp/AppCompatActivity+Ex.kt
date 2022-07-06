package com.example.mailapp

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showToast(message: String){
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}