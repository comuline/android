package com.comuline.app.domain

data class Station(
    val id: String = "",
    val name: String = "",
    val daop: Int = 1,
    val fgEnable: Int = 1,
    val haveSchedule: Boolean = false,
    val updatedAt: String = ""
)