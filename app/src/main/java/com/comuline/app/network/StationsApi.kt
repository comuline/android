package com.comuline.app.network

import com.comuline.app.network.model.StationApiModel
import retrofit2.http.GET

interface StationsApi {
    @GET("/v1/station/")
    suspend fun getStations(): StationApiModel
}