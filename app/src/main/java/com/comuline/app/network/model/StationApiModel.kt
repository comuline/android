package com.comuline.app.network.model

import com.comuline.app.database.StationEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StationData(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "daop")
    val daop: Int,
    @Json(name = "fgEnable")
    val fgEnable: Int,
    @Json(name = "haveSchedule")
    val haveSchedule: Boolean = false,
    @Json(name = "updatedAt")
    val updatedAt: String = "",
)

@JsonClass(generateAdapter = true)
data class StationApiModel (
    @Json(name = "status")
    val status: Int,
    @Json(name = "data")
    val data: List<StationData>,
)

fun StationApiModel.asDatabaseModel(): List<StationEntity> {
    return data.map {
        StationEntity(
            id = it.id,
            name = it.name,
            daop = it.daop,
            fgEnable = it.fgEnable,
            haveSchedule = it.haveSchedule,
            updatedAt = it.updatedAt
        )
    }
}