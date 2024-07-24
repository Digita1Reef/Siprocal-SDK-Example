package com.siprocal.sdkexample

import android.app.Application
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdk.client.SiprocalSDKSettings
import com.siprocal.sdk.client.notificationcenter.NotificationData
import com.siprocal.sdk.client.notificationcenter.NotificationDataListener
import com.siprocal.sdk.client.notificationcenter.NotificationEventListener
import com.siprocal.sdk.client.notificationcenter.NotificationEventType
import com.siprocal.sdkexample.data.local.db.AppDatabase
import com.siprocal.sdkexample.data.local.entity.Notification
import com.siprocal.sdkexample.data.repository.NotificationRepository
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainApplication : Application(), NotificationDataListener, NotificationEventListener {
    override fun onCreate() {
        super.onCreate()
        val siprocalSDKSettings = SiprocalSDKSettings.Builder()
            .enablePopupHandling()
            .build()
        SiprocalSDK.init(this, siprocalSDKSettings)
        SiprocalSDK.addNotificationDataListener(this)
        SiprocalSDK.addNotificationEventListener(this)
    }

    override fun onTerminate() {
        SiprocalSDK.removeNotificationDataListener()
        SiprocalSDK.removeNotificationEventListener()
        super.onTerminate()
    }

    override fun onNotificationDataListener(notificationData: NotificationData) {
        //use the notification data received
        val notification = Notification(
            title = notificationData.notificationTitle,
            message = notificationData.notificationDescription,
            createdAt = notificationData.createdAt,
            startedAt =  notificationData.startedAt,
            finalizedAt = notificationData.finalizedAt,
            actionType = notificationData.actionType,
            actionUrl = notificationData.actionUrl,
            timestamp = System.currentTimeMillis()
        )
        // Save the notification to the database in the background
        CoroutineScope(Dispatchers.IO).launch {
            saveNotification(notification)
        }
    }


    override fun onNotificationEventListener(
        adId: Long, notificationEventType: NotificationEventType.Type
    ) {
        //get the event from notificationEventType param
    }

    private suspend fun saveNotification(notification: Notification) {
        val notificationDao = AppDatabase.getDatabase(applicationContext).notificationDao()
        val repository = NotificationRepository(notificationDao)
        repository.insertNotification(notification)
    }
}