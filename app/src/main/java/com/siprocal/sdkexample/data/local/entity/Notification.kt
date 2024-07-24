package com.siprocal.sdkexample.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val createdAt: Long,
    val startedAt: Long,
    val finalizedAt: Long,
    val timestamp: Long,
    val actionType: Int,
    val actionUrl: String
)