package com.siprocal.sdkexample.data.local.db

import com.siprocal.sdkexample.data.local.dao.NotificationDao
import com.siprocal.sdkexample.data.local.entity.Notification
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Notification::class], version = 2)
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
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add the new column "adId" with a default value of 0
                db.execSQL("ALTER TABLE notifications ADD COLUMN adId INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}