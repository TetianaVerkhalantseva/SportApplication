package com.example.sportapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportapplication.database.entity.AchievedQuest

@Dao
interface AchievedQuestsDao {

    @Query("SELECT * FROM achieved_quests")
    suspend fun getAll(): List<AchievedQuest>

    @Query("SELECT * FROM achieved_quests")
    fun getAllLiveData(): LiveData<List<AchievedQuest>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: AchievedQuest)

}