package com.comuline.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.comuline.app.domain.Station

@Entity
data class StationEntity constructor(
    @PrimaryKey
    val id: String,
    val name: String,
    val daop: Int,
    val fgEnable: Int,
    val haveSchedule: Boolean,
    val updatedAt: String
)

fun List<StationEntity>.asDomainModel(): List<Station> {
    return map {
        Station(
            id = it.id,
            name = it.name,
            daop = it.daop,
            fgEnable = it.fgEnable,
            haveSchedule = it.haveSchedule,
            updatedAt = it.updatedAt
        )
    }
}