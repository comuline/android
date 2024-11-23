package com.comuline.app.network.model

import com.comuline.app.database.ScheduleEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduleData (
    @Json(name = "id")
    val id: String,
    @Json(name = "stationId")
    val stationId: String = "",
    @Json(name = "trainId")
    val trainId: String = "",
    @Json(name = "line")
    val line: String = "",
    @Json(name = "route")
    val route: String = "",
    @Json(name = "color")
    val color: String = "",
    @Json(name = "destination")
    val destination: String = "",
    @Json(name = "timeEstimated")
    val timeEstimated: String = "",
    @Json(name = "destinationTime")
    val destinationTime: String = "",
    @Json(name = "updatedAt")
    val updatedAt: String = "",
)

@JsonClass(generateAdapter = true)
data class ScheduleApiModel (
    @Json(name = "status")
    val status: Int,
    @Json(name = "data")
    val data: List<ScheduleData>,
)

fun ScheduleApiModel.asDomainModel(): List<ScheduleEntity> {
    return data.map {
        ScheduleEntity(
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