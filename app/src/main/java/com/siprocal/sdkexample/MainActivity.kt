package com.siprocal.sdkexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.github.clans.fab.FloatingActionButton
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdk.util.EnumManager
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var txtConsole: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtConsole = findViewById(R.id.console_output)
        val fab_1 = findViewById<FloatingActionButton>(R.id.menu_item_1)
        val fab_2 = findViewById<FloatingActionButton>(R.id.menu_item_2)

        fab_1.setOnClickListener {/* Implement functionality if needed */ }
        fab_2.setOnClickListener {
            refreshData()
        }
        refreshData()
    }

    private fun refreshData() {
        txtConsole.text = Utils.setTexColoForConsole("Refreshing...", Utils.COLOR_GREEN)
        handler.postDelayed({ getSDKData() }, 50)
    }

    private fun getSDKData() {
        CoroutineScope(Dispatchers.Main).launch {
            val sdkInfo = fetchSDKData()
            displaySDKData(sdkInfo)
        }
    }

    private suspend fun fetchSDKData(): Map<EnumManager.SdkInformation, String> {
        return withContext(Dispatchers.IO) {
            val sdkInfo = mutableMapOf<EnumManager.SdkInformation, String>()
            sdkInfo[EnumManager.SdkInformation.SDK_VERSION] =
                SiprocalSDK.getSdkInformation(EnumManager.SdkInformation.SDK_VERSION)
            sdkInfo[EnumManager.SdkInformation.BASE_ORG] =
                SiprocalSDK.getSdkInformation(EnumManager.SdkInformation.BASE_ORG)
            sdkInfo[EnumManager.SdkInformation.ORG] =
                SiprocalSDK.getSdkInformation(EnumManager.SdkInformation.ORG)
            sdkInfo[EnumManager.SdkInformation.STATE_SDK] =
                SiprocalSDK.getSdkInformation(EnumManager.SdkInformation.STATE_SDK)
            sdkInfo[EnumManager.SdkInformation.CLIENT_ID] =
                SiprocalSDK.getSdkInformation(EnumManager.SdkInformation.CLIENT_ID)
            sdkInfo[EnumManager.SdkInformation.SENSITIVE_DATA] =
                SiprocalSDK.getSdkInformation(EnumManager.SdkInformation.SENSITIVE_DATA)
            delay(1000)
            sdkInfo
        }
    }

    private fun displaySDKData(sdkInfo: Map<EnumManager.SdkInformation, String>) {
        sdkInfo.forEach { (key, value) ->
            txtConsole.append(
                Utils.setTexColoForConsole(
                    "${key.name} : $value\n",
                    Utils.COLOR_GREEN
                )
            )
        }
    }
}