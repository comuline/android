package com.comuline.app.domain

data class Station(
    val uid: String,
    val id: String,
    val name: String,
    val type: String,
    val daop: Int?,
    val fgEnable: Int?,
    val createdAt: String,
    val updatedAt: String
)