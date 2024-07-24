package com.siprocal.sdkexample.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siprocal.sdkexample.data.local.entity.Notification
import com.siprocal.sdkexample.data.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

    val notifications: LiveData<List<Notification>> = repository.lastTenNotifications

    fun deleteOldNotifications() {
        viewModelScope.launch {
            repository.deleteOldNotifications()
        }
    }
}