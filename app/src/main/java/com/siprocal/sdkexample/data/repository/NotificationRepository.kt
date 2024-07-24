package com.siprocal.sdkexample.data.repository

import androidx.lifecycle.LiveData
import com.siprocal.sdkexample.data.local.dao.NotificationDao
import com.siprocal.sdkexample.data.local.entity.Notification


class NotificationRepository(private val notificationDao: NotificationDao) {
    val lastTenNotifications: LiveData<List<Notification>> = notificationDao.getLastTenNotifications()
    suspend fun insertNotification(notification: Notification) {
        notificationDao.insertNotification(notification)
        notificationDao.deleteOldNotifications()
    }

    suspend fun deleteOldNotifications() {
        notificationDao.deleteOldNotifications()
    }

    suspend fun updateNotification(notification: Notification) {
        notificationDao.updateNotification(notification)
    }
}