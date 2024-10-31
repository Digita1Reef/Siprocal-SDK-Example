package com.siprocal.sdkexample.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.siprocal.sdk.client.SiprocalSDK
import com.siprocal.sdkexample.R
import com.siprocal.sdkexample.datastore.PreferenceDataStoreConstants
import com.siprocal.sdkexample.datastore.PreferenceDataStoreHelper
import kotlinx.coroutines.launch

class DialogSensitiveData(private val listenerDialog: ListenerDialogSensitive) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater.
            val inflater = requireActivity().layoutInflater;

            val dialogView = inflater.inflate(R.layout.popup_permission, null)

            val cancelButton =
                dialogView.findViewById<Button>(R.id.cancelButton) // Replace with actual button IDs
            val acceptButton =
                dialogView.findViewById<Button>(R.id.acceptButton) // Replace with actual button IDs

            // Set click listeners for the buttons
            cancelButton.setOnClickListener {
                // Handle button 1 click here
                lifecycleScope.launch {
                    context?.let { it1 ->
                        PreferenceDataStoreHelper(it1).putPreference(
                            PreferenceDataStoreConstants.IS_DATA_SENSITIVE_REQUESTED,
                            true
                        )
                    }
                }
                context?.let { SiprocalSDK.setSensitiveData(false) }
                listenerDialog.onDismissDialogSensitive()
                dismiss()
            }
            acceptButton.setOnClickListener {
                // Handle button 2 click here
                lifecycleScope.launch {
                    context?.let { it1 ->
                        PreferenceDataStoreHelper(it1).putPreference(
                            PreferenceDataStoreConstants.IS_DATA_SENSITIVE_REQUESTED,
                            true
                        )
                    }
                }
                context?.let { SiprocalSDK.setSensitiveData(true) }
                listenerDialog.onDismissDialogSensitive()
                dismiss()
            }
            builder.setView(dialogView)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface ListenerDialogSensitive {
        fun onDismissDialogSensitive()
    }
}