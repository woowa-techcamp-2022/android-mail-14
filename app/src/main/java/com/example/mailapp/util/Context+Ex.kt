package com.example.mailapp.util

import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.WindowManager
import kotlin.math.roundToInt

// 결과 값이 조금 이상하다 -> 태블릿이 아닌데 가로가 600dp 넘게 나온다
fun Context.getWindowWidthDp(): Int{
    var width: Int = 0
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).currentWindowMetrics.bounds.let {
            Log.d("TAG", "window get size[after${Build.VERSION_CODES.R}] width[${it.width()}], height[${it.height()}]")
            width = it.width()
        }
    }else{
        val size = Point().apply {
            (getSystemService(Context.DISPLAY_SERVICE) as DisplayManager).getDisplay(Display.DEFAULT_DISPLAY).getSize(this)
        }
        Log.d("TAG", "window get size[before${Build.VERSION_CODES.R}] width[${size.x}], height[${size.y}]")
        width = size.x
    }
    width = pxToDp(width)// width 를 dp로 변경
    Log.d("TAG", "window get size width dp[$width]")
    return width
}

fun Context.pxToDp(px: Int): Int {
    var density = resources.displayMetrics.density
    Log.d("TAG", "density[$density]")
    when(density){
        1.0f -> density *= 4.0f
        1.5f -> density *= (8.0f/3.0f)
        2.0f -> density *= 2.0f
    }
    Log.d("TAG", "density after process[$density]")
    return (px / density).roundToInt() // 반올림
}