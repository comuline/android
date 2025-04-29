package com.comuline.app.ui.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.comuline.app.constants.getLocationNameById
import com.comuline.app.domain.groupSchedules
//import com.comuline.app.domain.groupSchedules
import com.comuline.app.ui.components.Header
import com.comuline.app.ui.components.NoStations

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SchedulesScreen(onAddButtonClick: () -> Unit = {}) {
    val viewModel = hiltViewModel<ScheduleViewModel>()
    val uiState = viewModel.uiState

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        // Triggered when navigating to this screen
        viewModel.refreshSchedules()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> viewModel.startPolling()
                Lifecycle.Event.ON_PAUSE -> viewModel.stopPolling()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            viewModel.stopPolling()
        }
    }

    Column {
        Header(
            showSearchIcon = false,
            onAddButtonTap = {
                onAddButtonClick()
            }
        )
        if (uiState.savedStation.isEmpty()) {
            NoStations()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(uiState.savedStation) { item ->
                    val filteredSchedule = uiState.schedules.filter { it.stationId == item.id }
                    val groupedSchedule = groupSchedules(filteredSchedule)
                    getLocationNameById(item.id)?.let {
                        ScheduleItem(
                            stationName = it,
                            isLoading = filteredSchedule.isEmpty(),
                            groupedSchedule = groupedSchedule,
                            getStationById =  {
                                id -> viewModel.getStationById(id)
                            }
                        )
                    }
                }
            }
        }
    }
}
