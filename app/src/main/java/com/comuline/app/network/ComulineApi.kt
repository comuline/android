package com.comuline.app.network

import com.comuline.app.network.model.BaseApiModel
import com.comuline.app.network.model.ScheduleResponse
import com.comuline.app.network.model.StationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ComulineApi {
    @GET("/v1/station/")
    suspend fun getStations(): BaseApiModel<List<StationResponse>>

    @GET("v1/station/{stationId}")
    suspend fun getStation(
        @Path("stationId") stationId: String): BaseApiModel<StationResponse>

    @GET("v1/schedule/{stationId}")
    suspend fun getSchedule(
        @Path("stationId") stationId: String): BaseApiModel<List<ScheduleResponse>>
}