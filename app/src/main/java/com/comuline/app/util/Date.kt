package com.comuline.app.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

fun getCurrentTimeAsString(): String {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return timeFormat.format(Date())
}
