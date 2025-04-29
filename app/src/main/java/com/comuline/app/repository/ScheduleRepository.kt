package com.comuline.app.repository

import com.comuline.app.database.AppDatabase
import com.comuline.app.database.asDomainModel
import com.comuline.app.domain.Schedule
import com.comuline.app.domain.Station
import com.comuline.app.network.ComulineApi
import com.comuline.app.network.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val comulineApi: ComulineApi,
    private val appDatabase: AppDatabase
) {
    val schedules: Flow<List<Schedule>?> =
        appDatabase.comulineDao.getAllSchedules().map { it?.asDomainModel() }

    suspend fun getScheduleByStationId(stationId: String) {
        try {
            val schedules = comulineApi.getSchedule(stationId)
            appDatabase.comulineDao.insertSchedules(schedules.data.toEntity())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

//    suspend fun cleanupPastSchedules() {
//        val currentTime = getCurrentTimeAsString()
//        println("currentTime: $currentTime")
////        appDatabase.schedulesDao.deleteSchedulesWithPastDepartureTime(currentTime)
//    }
}