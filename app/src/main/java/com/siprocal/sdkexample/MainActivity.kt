package com.siprocal.sdkexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.siprocal.sdk.client.SiprocalSDK

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SiprocalSDK.showAvailableAd(this)

    }
}