package com.example.sportapplication.di

import android.content.Context
import com.example.sportapplication.database.AppDatabase
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.data.PoiStorage
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
    fun providePoiStorage(): PoiStorage {
        return PoiStorage()
    }
}