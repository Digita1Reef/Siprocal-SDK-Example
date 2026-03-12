package com.siprocal.sdkexample.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.siprocal.sdkexample.MainApplication
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val viewModel: NotificationViewModel by viewModels {
        (application as MainApplication).notificationViewModelFactory
    }

    private val notificationAdapter by lazy {
        NotificationAdapter(viewModel::markNotificationAsClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewNotifications.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }

        viewModel.notifications.observe(this) { notifications ->
            if (notifications.isEmpty()) {
                showNoNotificationsSnackbar()
            } else {
                notificationAdapter.submitList(notifications)
            }
        }

        showSnackbar()
    }

    private fun showSnackbar() {
        Snackbar.make(
            binding.root,
            getString(R.string.notification_center_limit_message),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.ok) {}.show()
    }

    private fun showNoNotificationsSnackbar() {
        Snackbar.make(
            binding.root,
            getString(R.string.no_recent_notifications),
            Snackbar.LENGTH_LONG
        ).show()
    }
}
