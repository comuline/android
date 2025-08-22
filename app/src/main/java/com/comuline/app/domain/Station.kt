package com.comuline.app.domain

import androidx.compose.runtime.Immutable

@Immutable
data class Station(
    val uid: String,
    val id: String,
    val name: String,
    val type: String,
    val active: Boolean?,
    val daop: Int?,
    val fgEnable: Int?,
    val createdAt: String,
    val updatedAt: String
)