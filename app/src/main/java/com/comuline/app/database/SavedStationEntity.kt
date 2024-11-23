package com.comuline.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedStationEntity constructor(
    @PrimaryKey
    val id: String,
)