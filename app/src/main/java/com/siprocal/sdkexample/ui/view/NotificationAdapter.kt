package com.siprocal.sdkexample.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.data.local.entity.Notification
import com.siprocal.sdkexample.utils.Utils

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private var notifications: List<Notification> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

    fun setNotifications(notifications: List<Notification>) {
        this.notifications = notifications
        notifyDataSetChanged()
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView = itemView.findViewById<TextView>(R.id.notificationTitle)
        private val messageTextView = itemView.findViewById<TextView>(R.id.notificationMessage)
        private val createdAtTextView = itemView.findViewById<TextView>(R.id.notificationcreatedAt)
        private val startedAtTextView = itemView.findViewById<TextView>(R.id.notificationstartedAt)
        private val finalizedAtTextView = itemView.findViewById<TextView>(R.id.notificationfinalizedAt)

        fun bind(notification: Notification) {
            titleTextView.text = notification.title
            messageTextView.text = notification.message
            createdAtTextView.text = "createdAt: ".plus(Utils.formatTimestamp(notification.createdAt))
            startedAtTextView.text = "startedAt: ".plus(Utils.formatTimestamp(notification.startedAt))
            finalizedAtTextView.text = "finalizedAt: ".plus(Utils.formatTimestamp(notification.finalizedAt))
        }
    }
}