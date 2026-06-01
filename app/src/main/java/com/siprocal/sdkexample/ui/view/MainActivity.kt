package com.siprocal.sdkexample.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.siprocal.sdk.client.EnumManager
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdkexample.MainApplication
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.databinding.ActivityMainBinding
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SiproLog"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NotificationViewModel by viewModels {
        (application as MainApplication).notificationViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_notifications -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }

                R.id.action_refresh -> {
                    refreshData()
                    true
                }

                else -> false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestNotificationPermission()
        }

        refreshData()
        SiprocalSDK.showAvailableAd(this)
    }

    private fun checkAndRequestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i(TAG, "POST_NOTIFICATIONS granted")
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.i(TAG, "POST_NOTIFICATIONS granted")
        } else {
            Log.i(TAG, "POST_NOTIFICATIONS denied")
        }
    }

    private fun refreshData() {
        setRefreshing(true)
        lifecycleScope.launch {
            val sdkInfo = fetchSdkData()
            displaySdkData(sdkInfo)
            viewModel.deleteOldNotifications()
            setRefreshing(false)
        }
    }

    private suspend fun fetchSdkData(): Map<EnumManager.SdkInformation, String> {
        return withContext(Dispatchers.IO) {
            val keys = listOf(
                EnumManager.SdkInformation.SDK_VERSION,
                EnumManager.SdkInformation.BASE_ORG,
                EnumManager.SdkInformation.ORG,
                EnumManager.SdkInformation.STATE_SDK,
                EnumManager.SdkInformation.CLIENT_ID,
                EnumManager.SdkInformation.SENSITIVE_DATA
            )

            val sdkInfo = keys.associateWith(SiprocalSDK::getSdkInformation).toMutableMap()
            sdkInfo
        }
    }

    private fun displaySdkData(sdkInfo: Map<EnumManager.SdkInformation, String>) {
        binding.sdkVersionValue.text = sdkInfo.displayValue(EnumManager.SdkInformation.SDK_VERSION)
        binding.baseOrgValue.text = sdkInfo.displayValue(EnumManager.SdkInformation.BASE_ORG)
        binding.organizationValue.text = sdkInfo.displayValue(EnumManager.SdkInformation.ORG)
        binding.stateValue.text = sdkInfo.displayValue(EnumManager.SdkInformation.STATE_SDK)
        binding.clientIdValue.text = sdkInfo.displayValue(EnumManager.SdkInformation.CLIENT_ID)
        binding.sensitiveDataValue.text =
            sdkInfo.displayValue(EnumManager.SdkInformation.SENSITIVE_DATA)
        binding.lastUpdatedLabel.text = getString(R.string.sdk_status_refreshed)
    }

    private fun setRefreshing(refreshing: Boolean) {
        binding.refreshProgress.isVisible = refreshing
        binding.lastUpdatedLabel.text = if (refreshing) {
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
