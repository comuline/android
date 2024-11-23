package com.comuline.app.network

import com.comuline.app.network.model.ScheduleApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {
    @GET("v1/schedule/{stationId}")
    suspend fun getSchedule(
        @Path("stationId") stationId: String,
        @Query("is_from_now") isFromNow: String): ScheduleApiModel
}