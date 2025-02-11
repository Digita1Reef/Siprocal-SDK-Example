package com.siprocal.sdkexample.ui.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.siprocal.sdk.client.EnumManager
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.data.local.db.AppDatabase
import com.siprocal.sdkexample.data.repository.NotificationRepository
import com.siprocal.sdkexample.data.repository.NotificationViewModelFactory
import com.siprocal.sdkexample.databinding.ActivityMainBinding
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import com.siprocal.sdkexample.utils.Utils
import kotlinx.coroutines.*

private const val TAG = "SiproLog"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var viewModel: NotificationViewModel
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
    }

    private fun checkAndRequestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i(TAG, "POST_NOTIFICATION granted")
            }

            else -> {
                requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS")
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.i(TAG, "POST_NOTIFICATION granted")
        } else {
            Log.i(TAG, "POST_NOTIFICATION denied")
        }
    }

    private fun refreshData() {
        binding.consoleOutput.text = Utils.setTexColoForConsole("Refreshing...", Utils.COLOR_GREEN)
        handler.postDelayed({ getSDKData() }, 50)
    }

    private fun getSDKData() {
        CoroutineScope(Dispatchers.Main).launch {
            val sdkInfo = fetchSDKData()
            binding.consoleOutput.text = Utils.setTexColoForConsole("", Utils.COLOR_GREEN)
            displaySDKData(sdkInfo)
            deleteOldNotifications()
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
            binding.consoleOutput.append(
                Utils.setTexColoForConsole(
                    "${key.name} : $value\n",
                    Utils.COLOR_GREEN
                )
            )
        }
    }

    private fun deleteOldNotifications() {
        val notificationDao = AppDatabase.getDatabase(applicationContext).notificationDao()
        val repository = NotificationRepository(notificationDao)
        val factory = NotificationViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteOldNotifications()
        }
    }
}