package com.khanhlh.firewarningkt.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khanhlh.firewarningkt.constant.Constants
import com.khanhlh.firewarningkt.data.local.dao.UserDao
import com.khanhlh.firewarningkt.data.local.model.User

/**
 * 页面描述：AppDatabase
 *
 * Created by ditclear on 2017/10/30.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, Constants.DB_NAME
            )
                .build()
    }

}