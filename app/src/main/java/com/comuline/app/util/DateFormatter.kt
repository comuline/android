package com.comuline.app.util

import java.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

fun formatDate(dateUTC: String?) : String {
    if (dateUTC.isNullOrEmpty()) return ""

    val date = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(dateUTC)
    val newFormat = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
    return date?.let { newFormat.format(it) }.orEmpty()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getRelativeTimeString(time: String): String {
    val (h, m) = time.split(":").map { it.toInt() }
    val date = LocalTime.of(h, m)
    val now = LocalTime.now(ZoneId.systemDefault())

    // Calculate seconds difference between the given time and now
    val deltaSeconds = ChronoUnit.SECONDS.between(now, date)

    // Define cutoffs in seconds
    val cutoffs = listOf(
        60,
        3600,
        86400,
        86400 * 7,
        86400 * 30,
        86400 * 365,
        Long.MAX_VALUE
    )

    // Define units as strings for the output
    val units = listOf(
        "second",
        "minute",
        "hour",
        "day",
        "week",
        "month",
        "year"
    )

    // Find the ideal cutoff unit
    val unitIndex = cutoffs.indexOfFirst { it > Math.abs(deltaSeconds) }

    // Get divisor for conversion to the right unit
    val divisor = if (unitIndex > 0) cutoffs[unitIndex - 1] else 1

    if (divisor == 0L) return "sekarang"

    // Get the number of units and format the relative time string
    val count = deltaSeconds / divisor
    val formattedTime = if (count == 0L) "sekarang" else "$count ${units[unitIndex]}"
    return formattedTime
}

fun removeSeconds(time: String): String {
    return time.split(":").take(2).joinToString(":")
}
