package com.comuline.app.domain

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

typealias GroupedDestination = Map<String, List<Schedule>>

typealias GroupedSchedule = Map<String, GroupedDestination>

@RequiresApi(Build.VERSION_CODES.O)
val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nX")

@RequiresApi(Build.VERSION_CODES.O)
fun groupSchedules(
    data: List<Schedule>, stationIdFilter: String? = null // Optional filter parameter
): GroupedSchedule {
    val now = OffsetDateTime.now()
    return data
        .filter { stationIdFilter == null || it.stationId == stationIdFilter } // Apply filter if provided
        .filter { x ->
            val date = OffsetDateTime.parse(x.departsAt, formatter)

            val scheduleHour = date.hour + date.minute / 60.0
            val nowHour = now.hour + now.minute / 60.0

            scheduleHour >= nowHour
        }
        .fold(mutableMapOf<String, MutableMap<String, MutableList<Schedule>>>()) { acc, schedule ->
            val lineKey = "${schedule.line}-${schedule.color}"
            val destKey = schedule.stationDestinationId

            val lineKeyRecord = acc.getOrPut(lineKey) { mutableMapOf() }
            val destKeyArray = lineKeyRecord.getOrPut(destKey) { mutableListOf() }

            destKeyArray.add(schedule)
            acc
    }
}