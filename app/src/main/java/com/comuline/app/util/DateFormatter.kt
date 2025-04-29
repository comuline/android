package com.comuline.app.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun getRelativeTimeString(datetimeStr: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nX")
    val dateTime = OffsetDateTime.parse(datetimeStr, formatter)

    val targetTime = dateTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES)
    val now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)

    val duration = Duration.between(now, targetTime)
    val totalMinutes = duration.toMinutes()

    val absMinutes = kotlin.math.abs(totalMinutes)
    val hours = absMinutes / 60
    val minutes = absMinutes % 60

    return when {
        totalMinutes < -1 -> {
            if (hours > 0) "$hours" + "h " + "${minutes}m ago" else "${minutes}m ago"
        }
        totalMinutes in -1..1 -> "now"
        else -> {
            if (hours > 0) "in $hours" + "h " + "${minutes}m" else "in ${minutes}m"
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatToHourMinute(datetimeStr: String): String {
    // Define the formatter for the input string
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nX")

    // Parse the input string into OffsetDateTime
    val dateTime = OffsetDateTime.parse(datetimeStr, inputFormatter)

    // Format to HH:mm
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return dateTime.format(outputFormatter)
}
