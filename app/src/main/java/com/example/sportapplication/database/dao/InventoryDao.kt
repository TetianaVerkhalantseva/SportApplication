package com.example.sportapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportapplication.database.entity.InventoryData

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventoryData")
    suspend fun getAll(): List<InventoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(inventoryData: InventoryData)

    @Query("SELECT COUNT(*) FROM inventoryData")
    suspend fun rowCount(): Int

    @Query("Delete From inventoryData")
    suspend fun deleteAll()

}