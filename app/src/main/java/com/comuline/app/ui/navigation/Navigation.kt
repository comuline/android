package com.comuline.app.ui.navigation

sealed class Route(val route: String) {
    data object Schedules : Route("schedules")
    data object Stations : Route("stations")
    data class StationDetail(val stationId: String) : Route("station_detail/$stationId") {
        companion object {
            const val route = "station_detail/{stationId}"
        }
    }
}