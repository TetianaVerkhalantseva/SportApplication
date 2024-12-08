package com.example.sportapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sportapplication.database.entity.InventoryData

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventoryData")
    suspend fun getAll(): List<InventoryData>

    @Query("SELECT * FROM  inventoryData where inventory_id = :inventoryId")
    suspend fun getInventoryDataById(inventoryId: Long): InventoryData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(inventoryData: InventoryData)

    @Query("SELECT COUNT(*) FROM inventoryData")
    suspend fun rowCount(): Int

    @Update
    suspend fun updateItem(inventoryData: InventoryData)

    @Query("Delete From inventoryData")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteItem(item: InventoryData)

}