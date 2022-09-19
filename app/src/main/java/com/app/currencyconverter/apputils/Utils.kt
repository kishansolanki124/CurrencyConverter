package com.app.currencyconverter.apputils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity?.showSnackBar(message: String?, ) {
    if (null != this && null != message) {
        hideKeyboard(this)
        Snackbar.make(
            this.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        ).show()
    }
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity) {
    val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}