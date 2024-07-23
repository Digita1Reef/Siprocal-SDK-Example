package com.siprocal.sdkexample.ui.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.siprocal.sdk.client.SiprocalSDK

class YourFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.from.equals(SiprocalSDK.getFCMSenderId())){
            SiprocalSDK.handleFCMMessage( message.data.toString())
            return
        }else{
            //your implementation
        }
    }
    override fun onNewToken(token: String) {
        // your code
        SiprocalSDK.refreshFCMToken(applicationContext)
    }
}