package com.comuline.app.ui.stations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comuline.app.R
import com.comuline.app.domain.Station
import com.comuline.app.ui.components.Header
import com.comuline.app.ui.components.NoNetwork
import kotlinx.coroutines.launch

@Composable
fun StationsScreen(
    onStationClick: (Station) -> Unit,
    onBackButtonTap: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<StationsViewModel>()
    val uiState = viewModel.uiState
    
    fun handleStationClick(station: Station) {
        coroutineScope.launch {
            viewModel.saveStation(station)
        }
        onStationClick(station)
    }


    if (uiState.offline) {
        NoNetwork()
    } else {
        Column {
            Header(
                showAddIcon = false,
                searchDisplay = "",
                onSearchDisplayChanged = {
                    searchVal ->
                    viewModel.searchStation(searchVal)
                },
                onSearchDisplayClosed = {
                    viewModel.resetSearch()
                },
                showBackButton = true,
                onBackButtonTap = {
                    onBackButtonTap()
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(
                    items = uiState.list,
                    key = { it.id }
                ) { item ->
                    StationItem(
                        item = item,
                        isSaved = { uiState.savedStation.any { v -> v.id == item.id } },
                        onStationClick = { station: Station ->
                            handleStationClick(station)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StationItem(
    item: Station, isSaved: () -> Boolean = { false },
    onStationClick: (Station) -> Unit)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStationClick(item) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = item.name,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1.0f))
        if(isSaved()){
            Icon(
                modifier = Modifier
                    .size(24.dp)
                ,
                painter = painterResource(R.drawable.baseline_close_24),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
            )
        }
    }
}