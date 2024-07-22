package com.siprocal.sdkexample

import android.app.Application
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdk.client.SiprocalSDKSettings

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val siprocalSDKSettings = SiprocalSDKSettings.Builder()
            .enablePopupHandling()
            .build()
        SiprocalSDK.init(this, siprocalSDKSettings)
    }
}