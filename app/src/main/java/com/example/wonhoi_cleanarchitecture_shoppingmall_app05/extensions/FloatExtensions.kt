package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.extensions

import android.content.res.Resources

internal fun Float.fromDpToPx() : Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}