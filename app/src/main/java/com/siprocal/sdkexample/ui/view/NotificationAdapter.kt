package com.siprocal.sdkexample.ui.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.data.local.entity.Notification
import com.siprocal.sdkexample.databinding.ItemNotificationBinding
import com.siprocal.sdkexample.utils.Utils

class NotificationAdapter(
    private val onNotificationClicked: (Notification) -> Unit
) : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(NotificationDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, onNotificationClicked)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        private val onNotificationClicked: (Notification) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(notification: Notification) {
            binding.notification = notification
            if (notification.clicked) {
                binding.notificationCardView.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.backgroundCardViewClicked)
                )
            } else {
                binding.notificationCardView.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.backgroundCardView)
                )
            }

            binding.notificationTitle.text = notification.title
            binding.notificationMessage.text = notification.message
            binding.notificationcreatedAt.text = context.getString(
                R.string.notification_created_at,
                Utils.formatTimestamp(notification.createdAt, Utils.DATE_FORMAT)
            )
            binding.notificationstartedAt.text = context.getString(
                R.string.notification_started_at,
                Utils.formatTimestamp(notification.startedAt, Utils.DATE_FORMAT)
            )
            binding.notificationfinalizedAt.text = context.getString(
                R.string.notification_finalized_at,
                Utils.formatTimestamp(notification.finalizedAt, Utils.DATE_FORMAT)
            )
            binding.notificationarrivedAt.text = context.getString(
                R.string.notification_arrived_at,
                Utils.formatTimestamp(notification.timestamp, Utils.DATE_TIME_FORMAT)
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

                    else -> {
                        Snackbar.make(
                            binding.root,
                            context.getString(R.string.notification_cta_not_implemented),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                if (!notification.clicked) {
                    onNotificationClicked(notification)
                    binding.notificationCardView.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.backgroundCardViewClicked)
                    )
                }
            }
        }
    }

    private object NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean =
            oldItem == newItem
    }
}
