package com.comuline.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.comuline.app.domain.Schedule

@Entity
data class ScheduleEntity constructor(
    @PrimaryKey
    val id: String,
    val stationId: String,
    val trainId: String,
    val line: String,
    val route: String,
    val color: String,
    val destination: String,
    val timeEstimated: String,
    val destinationTime: String,
    val updatedAt: String
)

fun List<ScheduleEntity>.asDomainModel(): List<Schedule> {
    return map {
        Schedule(
            id = it.id,
            stationId = it.stationId,
            trainId = it.trainId,
            line = it.line,
            route = it.route,
            color = it.color,
            destination = it.destination,
            timeEstimated = it.timeEstimated,
            destinationTime = it.destinationTime,
            updatedAt = it.updatedAt
        )
    }
}