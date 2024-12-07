package com.example.sportapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportapplication.database.entity.ItemsData

@Dao
interface ItemsDao {
    @Query("SELECT * FROM itemsData")
    suspend fun getAll(): List<ItemsData>

    @Query("SELECT * FROM itemsData WHERE item_id = :id")
    suspend fun getItemById(id: Int): ItemsData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemsData: ItemsData)

    @Query("SELECT COUNT(*) FROM itemsData")
    suspend fun rowCount(): Int

    @Query("Delete FROM itemsData")
    suspend fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE NAME='itemsData'")
    suspend fun resetPrimaryKey()

}