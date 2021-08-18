package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions

import android.content.Context
import android.widget.Toast

internal fun Context.toast(message:String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}