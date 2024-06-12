package com.example.diplom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule WHERE day = :day")
    suspend fun getScheduleForDay(day: String): List<ScheduleItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: ScheduleItem)

    @Update
    suspend fun updateSchedule(schedule: ScheduleItem)
}
