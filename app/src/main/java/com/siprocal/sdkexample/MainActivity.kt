package com.siprocal.sdkexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.siprocal.sdk.client.EnumManager
import com.siprocal.sdk.client.SiprocalSDK

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var sdkRefreshAttempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SiprocalSDK.showAvailableAd(this)
        refreshSdkStatus()
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun refreshSdkStatus() {
        setRefreshing(true)
        val sdkInfo = listOf(
            EnumManager.SdkInformation.SDK_VERSION,
            EnumManager.SdkInformation.ORG,
            EnumManager.SdkInformation.STATE_SDK
        ).associateWith(SiprocalSDK::getSdkInformation)

        findViewById<TextView>(R.id.sdkVersionValue).text =
            sdkInfo.displayValue(EnumManager.SdkInformation.SDK_VERSION)
        findViewById<TextView>(R.id.organizationValue).text =
            sdkInfo.displayValue(EnumManager.SdkInformation.ORG)
        findViewById<TextView>(R.id.stateValue).text =
            sdkInfo.displayValue(EnumManager.SdkInformation.STATE_SDK)
        setRefreshing(false)

        val sdkState = sdkInfo[EnumManager.SdkInformation.STATE_SDK].orEmpty()
        if (sdkState.equals("INITIAL", ignoreCase = true) && sdkRefreshAttempts < 6) {
            sdkRefreshAttempts++
            handler.postDelayed({ refreshSdkStatus() }, 10_000)
        }
    }

    private fun setRefreshing(refreshing: Boolean) {
        findViewById<View>(R.id.refreshProgress).visibility = if (refreshing) {
            View.VISIBLE
        } else {
            View.GONE
        }
        findViewById<TextView>(R.id.lastUpdatedLabel).text = if (refreshing) {
            getString(R.string.sdk_status_refreshing)
        } else {
            getString(R.string.sdk_status_refreshed)
        }
    }

    private fun Map<EnumManager.SdkInformation, String>.displayValue(
        key: EnumManager.SdkInformation
    ): String {
        return get(key).orEmpty().ifBlank { getString(R.string.value_placeholder) }
    }
}
