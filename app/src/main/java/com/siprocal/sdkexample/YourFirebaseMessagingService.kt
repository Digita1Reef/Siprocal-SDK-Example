package com.siprocal.sdkexample

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.siprocal.sdk.client.SiprocalSDK

class YourFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.from == SiprocalSDK.getFCMSenderId()) {
            SiprocalSDK.handleFCMMessage(message.data.toString())
            return
        }

        // Your implementation for non-Siprocal messages.
    }

    override fun onNewToken(token: String) {
        SiprocalSDK.refreshFCMToken(applicationContext)
    }
}
