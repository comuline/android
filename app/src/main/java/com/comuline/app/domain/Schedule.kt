package com.comuline.app.domain

data class Schedule(
    val id: String,
    val stationId: String,
    val stationOriginId: String,
    val stationDestinationId: String,
    val trainId: String,
    val line: String,
    val route: String,
    val departsAt: String,
    val arrivesAt: String,
    val color: String?, // Flattened for convenience
    val createdAt: String,
    val updatedAt: String
)
