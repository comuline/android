package com.comuline.app.network.model

import com.comuline.app.database.OriginColorEntity
import com.comuline.app.database.OriginEntity
import com.comuline.app.database.ScheduleEntity
import com.comuline.app.database.ScheduleMetadataEntity
import com.comuline.app.database.StationEntity
import com.comuline.app.database.StationMetadataEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduleResponse(
    @Json(name = "id")
    val id: String,

    @Json(name = "station_id")
    val stationId: String,

    @Json(name = "station_origin_id")
    val stationOriginId: String,

    @Json(name = "station_destination_id")
    val stationDestinationId: String,

    @Json(name = "train_id")
    val trainId: String,

    @Json(name = "line")
    val line: String,

    @Json(name = "route")
    val route: String,

    @Json(name = "departs_at")
    val departsAt: String,

    @Json(name = "arrives_at")
    val arrivesAt: String,

    @Json(name = "metadata")
    val metadata: ScheduleMetadata,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String
)

@JsonClass(generateAdapter = true)
data class ScheduleMetadata(
    @Json(name = "origin")
    val origin: OriginColor? = null
)

@JsonClass(generateAdapter = true)
data class OriginColor(
    @Json(name = "color")
    val color: String? = null
)

fun ScheduleResponse.toEntity() = ScheduleEntity(
    id = id,
    stationId = stationId,
    stationOriginId = stationOriginId,
    stationDestinationId = stationDestinationId,
    trainId = trainId,
    line = line,
    route = route,
    departsAt = departsAt,
    arrivesAt = arrivesAt,
    metadata = ScheduleMetadataEntity(
        origin = metadata.origin?.let {
            OriginColorEntity(color = it.color)
        }
    ),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<ScheduleResponse>.toEntity() : List<ScheduleEntity>{
    return map {
        it.toEntity()
    }
}