package com.khanhlh.firewarningkt.di

import android.app.Application
import androidx.room.Room
import com.khanhlh.firewarningkt.data.local.AppDatabase
import com.khanhlh.firewarningkt.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, "Entertainment.db"
        )
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}