package com.comuline.app.repository

import com.comuline.app.database.AppDatabase
import com.comuline.app.database.SavedStationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class WatchedStationRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {
    val stations: Flow<List<SavedStationEntity>?> =
        appDatabase.schedulesDao.getWatchedStations().map { it }


    fun saveStation(id: String) {
        try {
            appDatabase.schedulesDao.insertWatchedStation(SavedStationEntity(id))
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    fun removeStation(id: String) {
        try {
            appDatabase.schedulesDao.deleteWatchedStation(SavedStationEntity(id))
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}