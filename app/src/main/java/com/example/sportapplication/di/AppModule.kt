package com.example.sportapplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.sportapplication.database.AppDatabase
import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.AchievedQuestsDao
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.data.PoiStorage
import com.example.sportapplication.ui.settings.UnitViewModel
import com.example.sportapplication.ui.settings.batteryindicator.BatteryViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideAchievedEventsDao(appDatabase: AppDatabase): AchievedEventsDao = appDatabase.achievedEventsDao()


    @Provides
    @Singleton
    fun provideAchievedQuestsDao(appDatabase: AppDatabase): AchievedQuestsDao = appDatabase.achievedQuestsDao()

    @Provides
    @Singleton
    fun providePoiStorage(): PoiStorage {
        return PoiStorage()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUnitViewModel(sharedPreferences: SharedPreferences): UnitViewModel {
        return UnitViewModel(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesSensorDoa(appDatabase: AppDatabase): SensorDao = appDatabase.sensorDao()

    @Provides
    @Singleton
    fun provideBatteryViewModel(@ApplicationContext context: Context): BatteryViewModel {
        return BatteryViewModel(context.applicationContext as Application)
    }

}