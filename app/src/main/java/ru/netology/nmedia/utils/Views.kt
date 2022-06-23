package ru.netology.nmedia.utils

import android.content.Context
//import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputMethodManager
//import androidx.core.content.getSystemService

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}