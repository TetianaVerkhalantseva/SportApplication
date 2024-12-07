package com.example.sportapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.AchievedQuestsDao
import com.example.sportapplication.database.dao.InventoryDao
import com.example.sportapplication.database.dao.ItemsDao
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.entity.AchievedQuest
import com.example.sportapplication.database.entity.SensorData
import com.example.sportapplication.database.entity.User

@Database(entities = [User::class, SensorData::class, AchievedEvent::class, AchievedQuest::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun inventoryDao(): InventoryDao
    abstract fun itemsDao(): ItemsDao
    abstract fun userDao(): UserDao
    abstract fun sensorDao(): SensorDao
    abstract fun achievedEventsDao(): AchievedEventsDao
    abstract fun achievedQuestsDao(): AchievedQuestsDao
}