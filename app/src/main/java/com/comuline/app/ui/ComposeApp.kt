package com.comuline.app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comuline.app.ui.stations.StationsScreen
import com.comuline.app.ui.schedule.SchedulesScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeApp(snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "schedules",
        ) {
            composable("stations") {
                StationsScreen(
                    onStationClick = { station ->
                        navController.popBackStack()
                    },
                    onBackButtonTap = {
                        navController.popBackStack()
                    }
                )
            }
            composable("schedules") {
                SchedulesScreen(
                    onAddButtonClick = {
                        navController.navigate("stations")
                    }
                )
            }
        }
    }
}