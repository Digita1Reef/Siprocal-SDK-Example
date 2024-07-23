package com.siprocal.sdkexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siprocal.sdkexample.data.local.entity.Notification
import com.siprocal.sdkexample.data.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

    val notifications = MutableLiveData<List<Notification>>()

    fun insertNotification(notification: Notification) {
        viewModelScope.launch {
            repository.insertNotification(notification)
            fetchLastTenNotifications()
        }
    }

    fun fetchLastTenNotifications() {
        viewModelScope.launch {
            notifications.value = repository.getLastTenNotifications()
        }
    }

    fun deleteOldNotifications() {
        viewModelScope.launch {
            repository.deleteOldNotifications()
            fetchLastTenNotifications()
        }
    }
}