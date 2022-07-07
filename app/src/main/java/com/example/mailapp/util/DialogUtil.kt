package com.example.mailapp.util

import android.app.AlertDialog
import android.content.Context

object DialogUtil {
    fun show(context: Context, content: String, positiveBtn: String, positiveListener: () -> Unit){ //  positiveListener : (View) -> Unit )
        AlertDialog.Builder(context).apply {
            setCancelable(false)
            setMessage(content)
            setPositiveButton(positiveBtn) { _, _ ->
                positiveListener()
            }
        }.create().show()
    }
    fun show(context: Context, content: String, positiveBtn: String, negativeBtn: String, positiveListener: () -> Unit, negativeListener: () -> Unit ){ //  positiveListener : (View) -> Unit )
        AlertDialog.Builder(context).apply {
            setCancelable(false)
            setMessage(content)
            setPositiveButton(positiveBtn) { _, _ ->
                positiveListener()
            }
            setNegativeButton(negativeBtn) { _,_ ->
                negativeListener()
            }
        }.create().show()
    }
}