package com.comuline.app.repository

import com.comuline.app.database.AppDatabase
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.database.asDomainModel
import com.comuline.app.domain.Station
import com.comuline.app.network.ComulineApi
import com.comuline.app.network.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class StationRepository @Inject constructor(
    private val comulineApi: ComulineApi,
    private val appDatabase: AppDatabase
) {
    val stations: Flow<List<Station>?> =
        appDatabase.comulineDao.getStations().map { it?.asDomainModel() }

    suspend fun refreshStations() {
        try {
            val stations = comulineApi.getStations()
            stations.data.map { it.toEntity() }.let { appDatabase.comulineDao.insertStations(it) }
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    val savedStations: Flow<List<SavedStationEntity>?> =
        appDatabase.comulineDao.getWatchedStations().map { it }

    fun saveStation(id: String) {
        try {
            appDatabase.comulineDao.insertWatchedStation(SavedStationEntity(id))
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    fun removeStation(id: String) {
        try {
            appDatabase.comulineDao.deleteWatchedStation(SavedStationEntity(id))
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    suspend fun getSavedStationsOnce(): List<SavedStationEntity> {
        return appDatabase.comulineDao.getWatchedStationsOnce()
    }

    fun getStationDetail(id: String): Station? {
        return appDatabase.comulineDao.getStation(id)?.asDomainModel()
    }

    fun getStationFlow(id: String): Flow<Station?> {
        return appDatabase.comulineDao.getStationFlow(id).map { it?.asDomainModel() }
    }
}