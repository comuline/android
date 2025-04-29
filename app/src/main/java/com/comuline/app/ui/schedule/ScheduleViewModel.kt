package com.comuline.app.ui.schedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.domain.Station
import com.comuline.app.repository.ScheduleRepository
import com.comuline.app.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val stationsRepository: StationRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ScheduleUiState())
        private set

    private var pollingJob: Job? = null

    init {
        fetchOnStart()
        observeSchedules()
    }

    private fun fetchOnStart() {
        viewModelScope.launch {
            try {
                val savedStations = stationsRepository.getSavedStationsOnce()
                if (savedStations.isNullOrEmpty()) {
                    uiState = uiState.copy(offline = true)
                } else {
                    uiState = uiState.copy(savedStation = savedStations, offline = false)
                    refreshSchedules() // Changed from fetchSchedulesForStations
                }
            } catch (e: Exception) {
                Timber.e("Error fetching saved stations: $e")
                uiState = uiState.copy(offline = true)
            }
        }
    }

    fun refreshSchedules() {
        viewModelScope.launch {
            try {
                val stations = stationsRepository.getSavedStationsOnce()
                if (!stations.isNullOrEmpty()) {
                    uiState = uiState.copy(savedStation = stations)
                    fetchSchedulesForStations(stations)
                }
            } catch (e: Exception) {
                Timber.e("Error refreshing schedules: $e")
            }
        }
    }

    private suspend fun fetchSchedulesForStations(stations: List<SavedStationEntity>) {
        stations.forEach { station ->
            try {
                scheduleRepository.getScheduleByStationId(station.id)
            } catch (e: Exception) {
                Timber.e("Failed to fetch schedule for station ${station.id}: $e")
            }
        }
    }

    fun getStationById(id: String): Flow<Station?> {
        return stationsRepository.getStationFlow(id)
    }

    private fun observeSchedules() {
        viewModelScope.launch {
            scheduleRepository.schedules.collect { schedules ->
                Timber.d("Collected ${schedules?.size ?: 0} schedules")
                schedules?.let {
                    uiState = uiState.copy(schedules = it)
                }
            }
        }
    }

    fun startPolling(intervalMillis: Long = 30_000L) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (true) {
                try {
                    Timber.d("Polling schedules...")
                    val stations = stationsRepository.getSavedStationsOnce()
                    if (!stations.isNullOrEmpty()) {
                        fetchSchedulesForStations(stations)
                    }
                } catch (e: Exception) {
                    Timber.e("Polling error: $e")
                }
                delay(intervalMillis)
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}
