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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.siprocal.sdk.client.EnumManager
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdkexample.MainApplication
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.databinding.ActivityMainBinding
import com.siprocal.sdkexample.datastore.PreferenceDataStoreConstants
import com.siprocal.sdkexample.datastore.PreferenceDataStoreHelper
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import com.siprocal.sdkexample.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
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


        binding.menuItem1.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
            binding.fab.close(true)
        }
        binding.menuItem2.setOnClickListener {
            refreshData()
            binding.fab.close(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestNotificationPermission()
        }

        refreshData()

        SiprocalSDK.showAvailableAd(this)

        maybeShowSensitiveDataPermission()
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
        binding.consoleOutput.text =
            Utils.setTextColorForConsole("Refreshing...", Utils.colorGreen)
        lifecycleScope.launch {
            val sdkInfo = fetchSdkData()
            binding.consoleOutput.text = Utils.setTextColorForConsole("", Utils.colorGreen)
            displaySdkData(sdkInfo)
            viewModel.deleteOldNotifications()
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
            delay(1000)
            sdkInfo
        }
    }

    private fun displaySdkData(sdkInfo: Map<EnumManager.SdkInformation, String>) {
        sdkInfo.forEach { (key, value) ->
            binding.consoleOutput.append(
                Utils.setTextColorForConsole(
                    "${key.name} : $value\n",
                    Utils.colorGreen
                )
            )
        }
    }

    private fun maybeShowSensitiveDataPermission() {
        lifecycleScope.launch {
            val wasRequested = PreferenceDataStoreHelper(applicationContext).getPreference(
                PreferenceDataStoreConstants.IS_DATA_SENSITIVE_REQUESTED,
                false
            ).first()

            if (!wasRequested && supportFragmentManager.findFragmentByTag(DialogSensitiveData.TAG) == null) {
                DialogSensitiveData().show(supportFragmentManager, DialogSensitiveData.TAG)
            }
        }
    }
}
