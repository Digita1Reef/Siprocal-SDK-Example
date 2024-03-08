package com.siprocal.sdkexample

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class SiprocalFCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.from.equals(SiprocalSDK.INSTANCE.getFCMSenderId(applicationContext))){
            SiprocalSDK.INSTANCE.handleFCMMessage(applicationContext, message.data.toString())
            return
        }else{
            //your implementation
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // your code
        SiprocalSDK.INSTANCE.refreshFCMToken(applicationContext);
    }
}