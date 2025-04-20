package com.comuline.app.database

import androidx.room.*
import com.comuline.app.domain.Station

@Entity(tableName = "stations")
data class StationEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    val uid: String,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @Embedded(prefix = "metadata_")
    val metadata: StationMetadataEntity,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String
)

data class StationMetadataEntity(
    @Embedded(prefix = "origin_")
    val origin: OriginEntity? = null
)

data class OriginEntity(
    @ColumnInfo(name = "daop")
    val daop: Int? = null,

    @ColumnInfo(name = "fg_enable")
    val fgEnable: Int? = null
)

fun StationEntity.asDomainModel(): Station {
    return Station(
        uid = uid,
        id = id,
        name = name,
        type = type,
        daop = metadata.origin?.daop,
        fgEnable = metadata.origin?.fgEnable,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun List<StationEntity>.asDomainModel(): List<Station> {
    return map {
        Station(
            uid = it.uid,
            id = it.id,
            name = it.name,
            type = it.type,
            daop = it.metadata.origin?.daop,
            fgEnable = it.metadata.origin?.fgEnable,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
        )
    }
}
