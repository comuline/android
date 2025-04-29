package com.comuline.app.network.model

import com.comuline.app.database.OriginEntity
import com.comuline.app.database.StationEntity
import com.comuline.app.database.StationMetadataEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StationResponse(
    @Json(name = "uid")
    val uid: String,

    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "metadata")
    val metadata: StationMetadata,

    @Json(name = "created_at")
    val createdAt: String, // Use java.time.Instant if parsing as Date

    @Json(name = "updated_at")
    val updatedAt: String
)

@JsonClass(generateAdapter = true)
data class StationMetadata(
    @Json(name = "origin")
    val origin: Origin? = null
)

@JsonClass(generateAdapter = true)
data class Origin(
    @Json(name = "daop")
    val daop: Int? = null,

    @Json(name = "fg_enable")
    val fgEnable: Int? = null
)

fun StationResponse.toEntity() = StationEntity(
    uid = uid,
    id = id,
    name = name,
    type = type,
    metadata = StationMetadataEntity(
        origin = metadata.origin?.let {
            OriginEntity(daop = it.daop, fgEnable = it.fgEnable)
        }
    ),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<StationResponse>.toEntity() : List<StationEntity> {
    return map {
        StationEntity(it.uid, it.id, it.name, it.type, StationMetadataEntity(
            origin = it.metadata.origin?.let {
                OriginEntity(daop = it.daop, fgEnable = it.fgEnable)
            }
        ), it.createdAt, it.updatedAt)
    }
}

// TODO: remove no need
fun StationEntity.toApiModel() = StationResponse(
    uid = uid,
    id = id,
    name = name,
    type = type,
    metadata = StationMetadata(
        origin = metadata.origin?.let {
            Origin(daop = it.daop, fgEnable = it.fgEnable)
        }
    ),
    createdAt = createdAt,
    updatedAt = updatedAt
)