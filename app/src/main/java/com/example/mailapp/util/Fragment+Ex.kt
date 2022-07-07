package com.example.mailapp.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String){
    Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
}