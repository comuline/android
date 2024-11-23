package com.comuline.app.ui.schedule

import com.comuline.app.database.SavedStationEntity
import com.comuline.app.domain.Schedule

data class ScheduleUiState(
    val savedStation: List<SavedStationEntity> = listOf(),
    val schedules: List<Schedule> = listOf(),
    val offline: Boolean = false,
    val isRefreshing: Boolean = false
)