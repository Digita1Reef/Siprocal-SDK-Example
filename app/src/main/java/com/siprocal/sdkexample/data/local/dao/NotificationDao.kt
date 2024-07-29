package com.siprocal.sdkexample.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.siprocal.sdkexample.data.local.entity.Notification

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC LIMIT 10")
    fun getLastTenNotifications(): LiveData<List<Notification>>

    @Query("DELETE FROM notifications WHERE id NOT IN (SELECT id FROM notifications ORDER BY timestamp DESC LIMIT 10)")
    suspend fun deleteOldNotifications()

    @Update
    suspend fun updateNotification(notification: Notification)

    @Query("UPDATE notifications SET clicked = 1 WHERE adid = :adId")
    suspend fun updateClickedByActionId(adId: Long): Int
}