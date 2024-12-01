package com.example.sportapplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.sportapplication.database.AppDatabase
import com.example.sportapplication.database.dao.InventoryDao
import com.example.sportapplication.database.dao.ItemsDao
import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.data.PoiStorage
import com.example.sportapplication.ui.profile.ProfileViewModel
import com.example.sportapplication.ui.settings.batteryindicator.BatteryViewModel
import com.example.sportapplication.ui.settings.UnitViewModel
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
    fun provideProfileViewModel(userDao: UserDao): ProfileViewModel {
        return ProfileViewModel(userDao)
    }

    @Provides
    @Singleton
    fun providesSensorDoa(appDatabase: AppDatabase): SensorDao = appDatabase.sensorDao()

    @Provides
    @Singleton
    fun providesItemsDoa(appDatabase: AppDatabase): ItemsDao = appDatabase.itemsDao()

    @Provides
    @Singleton
    fun providesInventoryDoa(appDatabase: AppDatabase): InventoryDao = appDatabase.inventoryDao()

    @Provides
    @Singleton
    fun provideBatteryViewModel(@ApplicationContext context: Context): BatteryViewModel {
        return BatteryViewModel(context.applicationContext as Application)
    }

}