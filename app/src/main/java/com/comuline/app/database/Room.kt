package com.comuline.app.database

import com.comuline.app.database.StationEntity
import com.comuline.app.database.SavedStationEntity
import com.comuline.app.database.ScheduleEntity
import androidx.room.*
import com.comuline.app.dao.ComulineDao

@Database(entities = [
    StationEntity::class,
    SavedStationEntity::class,
    ScheduleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val comulineDao: ComulineDao
}