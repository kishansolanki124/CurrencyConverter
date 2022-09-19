package com.app.currencyconverter.apputils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

fun Activity?.showSnackBar(message: String?) {
    if (null != this && null != message) {
        hideKeyboard(this)
        Snackbar.make(
            this.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        ).show()
    }
}

fun hideKeyboard(activity: Activity) {
    val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

interface AppConstants {
    companion object{
        const val last_sync_time = "last_sync_time"
    }
}