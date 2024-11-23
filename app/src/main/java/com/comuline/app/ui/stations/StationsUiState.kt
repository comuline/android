package com.comuline.app.ui.stations

import com.comuline.app.database.SavedStationEntity
import com.comuline.app.domain.Station

data class StationsUiState(
    val list: List<Station> = listOf(),
    val savedStation: List<SavedStationEntity> = listOf(),
    val offline: Boolean = false,
    val isRefreshing: Boolean = false
)