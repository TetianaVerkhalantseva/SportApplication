package com.example.sportapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportapplication.database.entity.AchievedEvent

@Dao
interface AchievedEventsDao {

    @Query("SELECT * FROM achieved_events")
    suspend fun getAll(): List<AchievedEvent>

    @Query("SELECT * FROM achieved_events")
    fun getAllLiveData(): LiveData<List<AchievedEvent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: AchievedEvent)

}