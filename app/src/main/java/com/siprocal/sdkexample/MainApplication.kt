package com.siprocal.sdkexample

import android.app.Application
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdk.client.SiprocalSDKSettings

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val siprocalSDKSettings: SiprocalSDKSettings = SiprocalSDKSettings.Builder(this)
            .enablePopupHandling()
            .build()
        SiprocalSDK.INSTANCE.init(siprocalSDKSettings, true)
    }
}