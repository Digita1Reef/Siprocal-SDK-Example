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
import com.siprocal.sdkexample.data.repository.NotificationViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainApplication : Application(), NotificationDataListener, NotificationEventListener {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val notificationRepository: NotificationRepository by lazy {
        NotificationRepository(database.notificationDao())
    }
    val notificationViewModelFactory: NotificationViewModelFactory by lazy {
        NotificationViewModelFactory(notificationRepository)
    }

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
            startedAt = notificationData.startedAt,
            finalizedAt = notificationData.finalizedAt,
            actionType = notificationData.actionType,
            actionUrl = notificationData.actionUrl,
            timestamp = System.currentTimeMillis(),
            adId = notificationData.adId
        )
        applicationScope.launch {
            notificationRepository.insertNotification(notification)
        }
    }

    override fun onNotificationEventListener(
        adId: Long, notificationEventType: NotificationEventType.Type
    ) {
        val isClickOrDismiss = notificationEventType == NotificationEventType.Type.CLICK ||
            notificationEventType == NotificationEventType.Type.CLOSED_NOTIFICATION

        if (isClickOrDismiss) {
            applicationScope.launch {
                notificationRepository.updateClickedByActionId(adId)
            }
        }
    }
}
