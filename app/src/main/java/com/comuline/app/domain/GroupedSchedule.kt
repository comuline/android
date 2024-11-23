package com.comuline.app.domain

import com.comuline.app.util.removeSeconds

typealias GroupedDestination = Map<String, List<Schedule>>

typealias GroupedSchedule = Map<String, GroupedDestination>

fun groupSchedules(
    data: List<Schedule>, stationIdFilter: String? = null // Optional filter parameter
): GroupedSchedule {
    return data
        .filter { stationIdFilter == null || it.stationId == stationIdFilter } // Apply filter if provided
        .fold(mutableMapOf<String, MutableMap<String, MutableList<Schedule>>>()) { acc, schedule ->
        val lineKey = "${schedule.line}-${schedule.color}"
        val destKey = schedule.destination

        val lineKeyRecord = acc.getOrPut(lineKey) { mutableMapOf() }
        val destKeyArray = lineKeyRecord.getOrPut(destKey) { mutableListOf() }

        val updatedSchedule = schedule.copy(
            timeEstimated = removeSeconds(schedule.timeEstimated),
            destinationTime = removeSeconds(schedule.destinationTime)
        )

        destKeyArray.add(updatedSchedule)

        acc
    }
}

val GROUPED_SCHEDULES_SAMPLES = groupSchedules(SCHEDULES_SAMPLES)

val DESTINATION_SAMPLES = GROUPED_SCHEDULES_SAMPLES.get("COMMUTER LINE BST-#0084D8")