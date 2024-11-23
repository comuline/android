package com.comuline.app.ui.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.comuline.app.constants.getLocationNameById
import com.comuline.app.domain.groupSchedules
import com.comuline.app.ui.components.Header
import com.comuline.app.ui.components.NoStations

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SchedulesScreen(onAddButtonClick: () -> Unit = {}) {
    val viewModel = hiltViewModel<ScheduleViewModel>()
    val uiState = viewModel.uiState

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
                            groupedSchedule = groupedSchedule
                        )
                    }
                }
            }
        }
    }
}
