package com.comuline.app.ui.schedule

import androidx.compose.runtime.Immutable
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.domain.Schedule

@Immutable
sealed interface ScheduleUiState {
    @Immutable
    data object Loading : ScheduleUiState
    
    @Immutable
    data class Success(
        val savedStations: List<SavedStationEntity> = emptyList(),
        val schedules: List<Schedule> = emptyList(),
        val isRefreshing: Boolean = false
    ) : ScheduleUiState
    
    @Immutable
    data class Error(
        val message: String,
        val isOffline: Boolean = false
    ) : ScheduleUiState
    
    @Immutable
    data object Empty : ScheduleUiState
}

data class LegacyScheduleUiState(
    val savedStation: List<SavedStationEntity> = listOf(),
    val schedules: List<Schedule> = listOf(),
    val offline: Boolean = false,
    val isRefreshing: Boolean = false
)