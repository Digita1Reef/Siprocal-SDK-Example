package com.siprocal.sdkexample.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.siprocal.sdkexample.MainApplication
import com.siprocal.sdkexample.ui.viewmodel.NotificationViewModel
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerViewNotifications.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }

        viewModel.notifications.observe(this) { notifications ->
            notificationAdapter.submitList(notifications)
            binding.emptyState.isVisible = notifications.isEmpty()
            binding.recyclerViewNotifications.isVisible = notifications.isNotEmpty()
        }
    }
}
