package com.comuline.app.database

import androidx.room.*
import com.comuline.app.dao.SchedulesDao

@Database(entities = [
    StationEntity::class,
    SavedStationEntity::class,
    ScheduleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val schedulesDao: SchedulesDao
}