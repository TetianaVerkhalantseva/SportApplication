package com.example.sportapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.AchievedQuestsDao
import com.example.sportapplication.database.dao.InventoryDao
import com.example.sportapplication.database.dao.ItemsDao
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.entity.InventoryData
import com.example.sportapplication.database.entity.ItemsData
import com.example.sportapplication.database.entity.AchievedQuest
import com.example.sportapplication.database.entity.SensorData
import com.example.sportapplication.database.entity.User

@Database(entities = [User::class, SensorData::class, AchievedEvent::class, ItemsData::class, InventoryData::class, AchievedQuest::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
    abstract fun itemsDao(): ItemsDao
    abstract fun userDao(): UserDao
    abstract fun sensorDao(): SensorDao
    abstract fun achievedEventsDao(): AchievedEventsDao
    abstract fun achievedQuestsDao(): AchievedQuestsDao
}