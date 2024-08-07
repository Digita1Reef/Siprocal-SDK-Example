package com.siprocal.sdkexample.ui.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.data.local.entity.Notification
import com.siprocal.sdkexample.data.repository.NotificationRepository
import com.siprocal.sdkexample.databinding.ItemNotificationBinding
import com.siprocal.sdkexample.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationAdapter(private val context: Context, private val repository: NotificationRepository) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private var notifications: List<Notification> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, context, repository)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

    fun setNotifications(notifications: List<Notification>) {
        this.notifications = notifications
        notifyDataSetChanged()
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        private val context: Context,
        private val repository: NotificationRepository
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(notification: Notification) {
            binding.notification = notification
            if (notification.clicked) {
                binding.notificationCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.backgroundCardViewClicked))
            } else {
                binding.notificationCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.backgroundCardView))
            }

            binding.notificationTitle.text = notification.title
            binding.notificationMessage.text = notification.message
            binding.notificationcreatedAt.text =
                "createdAt: ".plus(Utils.formatTimestamp(notification.createdAt, Utils.DATE_FORMAT))
            binding.notificationstartedAt.text =
                "startedAt: ".plus(Utils.formatTimestamp(notification.startedAt, Utils.DATE_FORMAT))
            binding.notificationfinalizedAt.text = "finalizedAt: ".plus(
                Utils.formatTimestamp(
                    notification.finalizedAt,
                    Utils.DATE_FORMAT
                )
            )
            binding.notificationarrivedAt.text = "arrivedAt: ".plus(
                Utils.formatTimestamp(
                    notification.timestamp,
                    Utils.DATE_TIME_FORMAT
                )
            )

            binding.root.setOnClickListener {
                when (notification.actionType) {
                    1 -> { // Open URL
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(notification.actionUrl))
                        context.startActivity(browserIntent)
                    }

                    2 -> { // Open deeplink
                        val deeplinkIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(notification.actionUrl))
                        context.startActivity(deeplinkIntent)
                    }

                    3 -> { // Call phone number
                        val phoneIntent =
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${notification.actionUrl}"))
                        context.startActivity(phoneIntent)
                    }

                    else -> { // Unsupported actionType
                        Snackbar.make(
                            binding.root,
                            "CTA not implemented in this example",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                // Mark the notification as clicked and update in the database
                if (!notification.clicked) {
                    notification.clicked = true
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.updateNotification(notification)
                    }
                    binding.notificationCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.backgroundCardViewClicked))
                }
            }
        }
    }
}