package com.siprocal.sdkexample.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.siprocal.sdkexample.data.local.db.AppDatabase
import com.siprocal.sdkexample.data.repository.NotificationRepository
import com.siprocal.sdkexample.data.repository.NotificationViewModelFactory
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.siprocal.sdkexample.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notificationDao = AppDatabase.getDatabase(applicationContext).notificationDao()
        val repository = NotificationRepository(notificationDao)
        val factory = NotificationViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NotificationViewModel::class.java]

        notificationAdapter = NotificationAdapter(this, repository)
        binding.recyclerViewNotifications.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }

        viewModel.notifications.observe(this) { notifications ->
            notificationAdapter.setNotifications(notifications)
        }

        viewModel.fetchLastTenNotifications()
        showSnackbar()
    }
    private fun showSnackbar() {
        Snackbar.make(
            binding.root,
            "Only the last 10 notifications will be shown in this section",
            Snackbar.LENGTH_LONG
        ).setAction("OK") {
            // Acción cuando se presiona el botón OK, el Snackbar se cerrará automáticamente
        }.show()
    }
}