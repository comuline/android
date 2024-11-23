package com.comuline.app.dao

import androidx.room.*
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.database.ScheduleEntity
import com.comuline.app.database.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SchedulesDao {

    @Query("select * from StationEntity")
    fun getStations(): Flow<List<StationEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStations(station: List<StationEntity>)

    @Query("select * from SavedStationEntity")
    fun getWatchedStations(): Flow<List<SavedStationEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWatchedStation(station: SavedStationEntity)

    @Delete
    fun deleteWatchedStation(station: SavedStationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Query("SELECT * FROM ScheduleEntity WHERE stationId = :stationId")
    suspend fun getSchedulesByStationId(stationId: String): List<ScheduleEntity>

    @Query("SELECT * FROM ScheduleEntity")
    fun getAllSchedules(): Flow<List<ScheduleEntity>?>

    @Query("SELECT * FROM ScheduleEntity WHERE id = :id")
    suspend fun getScheduleById(id: String): ScheduleEntity?

    @Query("DELETE FROM ScheduleEntity WHERE id = :id")
    suspend fun deleteScheduleById(id: String)

    @Query("DELETE FROM ScheduleEntity")
    suspend fun deleteAllSchedules()
}