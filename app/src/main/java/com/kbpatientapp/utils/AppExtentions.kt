package com.kbpatientapp.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.shareOnWhatsApp(message: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        showToast("WhatsApp not installed.")
    }
}