package com.siprocal.sdkexample.data.local.db

import com.siprocal.sdkexample.data.local.dao.NotificationDao
import com.siprocal.sdkexample.data.local.entity.Notification
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Notification::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_siprocal_demo"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}