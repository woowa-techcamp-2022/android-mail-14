package com.example.mailapp

import android.view.View

fun Boolean.toVisible(): Int {
    return if(this) View.VISIBLE else View.GONE
}