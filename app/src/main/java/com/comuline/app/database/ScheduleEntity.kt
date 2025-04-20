package com.comuline.app.database

import androidx.room.*
import com.comuline.app.domain.Schedule

@Entity(tableName = "schedules")
data class ScheduleEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "station_id")
    val stationId: String,

    @ColumnInfo(name = "station_origin_id")
    val stationOriginId: String,

    @ColumnInfo(name = "station_destination_id")
    val stationDestinationId: String,

    @ColumnInfo(name = "train_id")
    val trainId: String,

    @ColumnInfo(name = "line")
    val line: String,

    @ColumnInfo(name = "route")
    val route: String,

    @ColumnInfo(name = "departs_at")
    val departsAt: String,

    @ColumnInfo(name = "arrives_at")
    val arrivesAt: String,

    @Embedded(prefix = "metadata_")
    val metadata: ScheduleMetadataEntity,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String
)

data class ScheduleMetadataEntity(
    @Embedded(prefix = "origin_")
    val origin: OriginColorEntity? = null
)

data class OriginColorEntity(
    @ColumnInfo(name = "color")
    val color: String? = null
)


fun List<ScheduleEntity>.asDomainModel(): List<Schedule> {
    return map {
        Schedule(
            id = it.id,
            stationId = it.stationId,
            stationOriginId = it.stationOriginId,
            stationDestinationId = it.stationDestinationId,
            trainId = it.trainId,
            line = it.line,
            route = it.route,
            departsAt = it.departsAt,
            arrivesAt = it.arrivesAt,
            color = it.metadata.origin?.color,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }
}