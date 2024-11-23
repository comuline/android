package com.comuline.app.ui.schedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.repository.ScheduleRepository
import com.comuline.app.repository.WatchedStationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val watchedStationRepository: WatchedStationRepository
) : ViewModel() {

    var uiState by mutableStateOf(ScheduleUiState())
        private set

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            withContext(Dispatchers.Main) {
//                launch {
//                    watchedStationRepository.stations.collect { list ->
//                        uiState = list?.let { uiState.copy(savedStation = it) }!!
//                    }
//                }
//                launch {
//                    watchedStationRepository.stations.collect { list ->
//                        uiState = if (list.isNullOrEmpty()) {
//                            uiState.copy(offline = true)
//                        } else {
//                            uiState.copy(
//                                savedStation = list,
//                                offline = false
//                            )
//                        }
//                    }
//                }
//            }
//        }

        viewModelScope.launch(Dispatchers.IO) {
            watchedStationRepository.stations.collect { list ->
                withContext(Dispatchers.Main) {
                    if (list.isNullOrEmpty()) {
//                        uiState = uiState.copy(offline = true)
                    } else {
                        uiState = uiState.copy(savedStation = list, offline = false)
                        fetchSchedulesForStations(list)
                        observeSchedules()
                    }
                }
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

    private fun observeSchedules() {
        viewModelScope.launch {
            scheduleRepository.schedules.collect { schedules ->
                schedules?.let {
                    uiState = uiState.copy(schedules = it)
//                    // Group schedules by stationId or other criteria
//                    val grouped = it.groupBy { schedule -> schedule.stationId }
//
//                    grouped.map { schedules ->
//                        val groupedSchedule = groupSchedules(schedules.value)
//                        uiState = uiState.copy(schedules = groupedSchedule)
//                    }
//
//                    val groupedSchedule = groupSchedules(it)
//                    uiState = uiState.copy(schedules = groupedSchedule)
                }
            }
        }
    }
}

//                launch {
//                    uiState.savedStation.map {
//                        scheduleRepository.getScheduleByStationId(it.id)
//                    }
//                }

//    private suspend fun filterWithQuery(query: String) {
//        stationsRepository.stations.collect { list ->
//            withContext(Dispatchers.Main) {
//                uiState = if (list.isNullOrEmpty()) {
//                    uiState.copy(offline = true)
//                } else {
//                    if(query.isNotEmpty()){
//                        val filteredList = ArrayList<Station>()
//                        for(station in list){
//                            if(station.name.contains(query, ignoreCase = true)){
//                                filteredList.add(station)
//                            }
//                        }
//                        uiState.copy(
//                            list = filteredList,
//                        )
//                    } else {
//                        uiState.copy(
//                            list = list,
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    fun searchStation(search : String) {
//        searchJob = viewModelScope.launch {
//            delay(500)
//            filterWithQuery(search)
//        }
//    }
//
//    fun resetSearch() {
//        viewModelScope.launch {
//            filterWithQuery("")
//        }
//    }
//
//    suspend fun saveStation(station: Station) {
//        withContext(Dispatchers.IO) {
//            launch {
//                if(uiState.savedStation.any { v -> v.id == station.id }){
//                    watchedStationRepository.removeStation(station.id)
//                    val savedMessage = "${station.name} Removed"
//
//                    SnackbarController.sendEvent(event = SnackbarEvent(
//                        message = savedMessage,
////                action = SnackbarAction(name = "Undo", action = {
////                })
//                    ))
//                } else {
//                    watchedStationRepository.saveStation(station.id)
//                    val savedMessage = "${station.name} Saved"
//
//                    SnackbarController.sendEvent(event = SnackbarEvent(
//                        message = savedMessage,
////                action = SnackbarAction(name = "Undo", action = {
////                })
//                    ))
//                }
//            }
//        }
//    }