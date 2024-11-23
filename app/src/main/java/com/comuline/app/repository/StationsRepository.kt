package com.comuline.app.repository

import com.comuline.app.database.AppDatabase
import com.comuline.app.database.asDomainModel
import com.comuline.app.domain.Station
import com.comuline.app.network.StationsApi
import com.comuline.app.network.model.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class StationsRepository @Inject constructor(
    private val stationsApi: StationsApi,
    private val appDatabase: AppDatabase
) {
    val stations: Flow<List<Station>?> =
        appDatabase.schedulesDao.getStations().map { it?.asDomainModel() }

    suspend fun refreshStations() {
        try {
            val stations = stationsApi.getStations()
            appDatabase.schedulesDao.insertStations(stations.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}