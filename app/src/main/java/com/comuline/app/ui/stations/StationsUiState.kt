package com.comuline.app.ui.stations

import androidx.compose.runtime.Immutable
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.domain.Station

@Immutable
sealed interface StationsUiState {
    @Immutable
    data object Loading : StationsUiState
    
    @Immutable
    data class Success(
        val stations: List<Station> = emptyList(),
        val savedStations: List<SavedStationEntity> = emptyList(),
        val isRefreshing: Boolean = false
    ) : StationsUiState
    
    @Immutable
    data class Error(
        val message: String,
        val isOffline: Boolean = false
    ) : StationsUiState
}

data class LegacyStationsUiState(
    val list: List<Station> = listOf(),
    val savedStation: List<SavedStationEntity> = listOf(),
    val offline: Boolean = false,
    val isRefreshing: Boolean = false
)