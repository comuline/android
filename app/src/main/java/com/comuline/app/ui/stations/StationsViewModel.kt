package com.comuline.app.ui.stations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comuline.app.controller.SnackbarController
import com.comuline.app.controller.SnackbarEvent
import com.comuline.app.domain.Station
import com.comuline.app.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val stationsRepository: StationRepository,
) : ViewModel() {

    private var searchJob: Job? = null
    var uiState by mutableStateOf(StationsUiState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            stationsRepository.refreshStations()
            withContext(Dispatchers.Main) {
                launch {
                    stationsRepository.savedStations.collect { list ->
                        uiState = list?.let { uiState.copy(savedStation = it) }!!
                    }
                }
                launch {
                    stationsRepository.stations.collect { list ->
                        uiState = if (list.isNullOrEmpty()) {
                            uiState.copy(offline = true)
                        } else {
                            uiState.copy(
                                list = list,
                                offline = false
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun filterWithQuery(query: String) {
        stationsRepository.stations.collect { list ->
            withContext(Dispatchers.Main) {
                uiState = if (list.isNullOrEmpty()) {
                    uiState.copy(offline = true)
                } else {
                    if(query.isNotEmpty()){
                        val filteredList = ArrayList<Station>()
                        for(station in list){
                            if(station.name.contains(query, ignoreCase = true)){
                                filteredList.add(station)
                            }
                        }
                        uiState.copy(
                            list = filteredList,
                        )
                    } else {
                        uiState.copy(
                            list = list,
                        )
                    }
                }
            }
        }
    }

    fun searchStation(search : String) {
        searchJob = viewModelScope.launch {
            delay(500)
            filterWithQuery(search)
        }
    }

    fun resetSearch() {
        viewModelScope.launch {
            filterWithQuery("")
        }
    }

    suspend fun saveStation(station: Station) {
        withContext(Dispatchers.IO) {
            launch {
                if(uiState.savedStation.any { v -> v.id == station.id }){
                    stationsRepository.removeStation(station.id)
                    val savedMessage = "${station.name} Removed"

                    SnackbarController.sendEvent(event = SnackbarEvent(
                        message = savedMessage,
//                action = SnackbarAction(name = "Undo", action = {
//                })
                    ))
                } else {
                    stationsRepository.saveStation(station.id)
                    val savedMessage = "${station.name} Saved"

                    SnackbarController.sendEvent(event = SnackbarEvent(
                        message = savedMessage,
//                action = SnackbarAction(name = "Undo", action = {
//                })
                    ))
                }
            }
        }
    }
}