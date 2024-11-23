package com.comuline.app.repository

import com.comuline.app.database.AppDatabase
import com.comuline.app.database.asDomainModel
import com.comuline.app.domain.Schedule
import com.comuline.app.network.ScheduleApi
import com.comuline.app.network.model.asDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleApi: ScheduleApi,
    private val appDatabase: AppDatabase
) {
    val schedules: Flow<List<Schedule>?> =
        appDatabase.schedulesDao.getAllSchedules().map { it?.asDomainModel() }

    suspend fun getScheduleByStationId(stationId: String) {
        try {
            val schedules = scheduleApi.getSchedule(stationId, "true")
            appDatabase.schedulesDao.insertSchedules(schedules.asDomainModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}