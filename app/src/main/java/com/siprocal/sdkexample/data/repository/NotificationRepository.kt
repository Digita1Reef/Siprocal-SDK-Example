package com.siprocal.sdkexample.data.repository

import com.siprocal.sdkexample.data.local.dao.NotificationDao
import com.siprocal.sdkexample.data.local.entity.Notification


class NotificationRepository(private val notificationDao: NotificationDao) {

    suspend fun insertNotification(notification: Notification) {
        notificationDao.insertNotification(notification)
        notificationDao.deleteOldNotifications()
        notificationDao.deleteOldNotifications()
    }

    suspend fun getLastTenNotifications(): List<Notification> {
        return notificationDao.getLastTenNotifications()
    }

    suspend fun deleteOldNotifications() {
        notificationDao.deleteOldNotifications()
    }

    suspend fun updateNotification(notification: Notification) {
        notificationDao.updateNotification(notification)
    }
}