package com.comuline.app.dao

import androidx.room.*
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.database.ScheduleEntity
import com.comuline.app.database.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComulineDao {
    @Query("select * from stations")
    fun getStations(): Flow<List<StationEntity>?>

    @Query("select * from stations where id = :id")
    fun getStation(id: String): StationEntity?

    @Query("SELECT * FROM stations WHERE id = :id LIMIT 1")
    fun getStationFlow(id: String): Flow<StationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStations(station: List<StationEntity>)

    @Query("select * from SavedStationEntity")
    fun getWatchedStations(): Flow<List<SavedStationEntity>?>

    @Query("SELECT * FROM SavedStationEntity")
    suspend fun getWatchedStationsOnce(): List<SavedStationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWatchedStation(station: SavedStationEntity)

    @Delete
    fun deleteWatchedStation(station: SavedStationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Query("SELECT * FROM schedules WHERE station_id = :stationId")
    suspend fun getSchedulesByStationId(stationId: String): List<ScheduleEntity>

    @Query("SELECT * FROM schedules")
    fun getAllSchedules(): Flow<List<ScheduleEntity>?>

    @Query("SELECT * FROM schedules WHERE id = :id")
    suspend fun getScheduleById(id: String): ScheduleEntity?

    @Query("DELETE FROM schedules WHERE id = :id")
    suspend fun deleteScheduleById(id: String)

    @Query("DELETE FROM schedules")
    suspend fun deleteAllSchedules()

    // Not sure if it's works
    @Query("DELETE FROM schedules WHERE departs_at < :currentTime")
    suspend fun deleteSchedulesWithPastDepartureTime(currentTime: String)
}