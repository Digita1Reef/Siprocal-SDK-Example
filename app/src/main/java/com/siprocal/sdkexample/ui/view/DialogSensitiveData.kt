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

class DialogSensitiveData : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val dialogView = inflater.inflate(R.layout.popup_permission, null)
            val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
            val acceptButton = dialogView.findViewById<Button>(R.id.acceptButton)

            cancelButton.setOnClickListener { persistSensitiveDataChoice(false) }
            acceptButton.setOnClickListener { persistSensitiveDataChoice(true) }

            builder.setView(dialogView)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun persistSensitiveDataChoice(enabled: Boolean) {
        val appContext = context?.applicationContext ?: return
        lifecycleScope.launch {
            PreferenceDataStoreHelper(appContext).putPreference(
                PreferenceDataStoreConstants.IS_DATA_SENSITIVE_REQUESTED,
                true
            )
            SiprocalSDK.setSensitiveData(enabled)
            dismiss()
        }
    }

    companion object {
        const val TAG = "SENSITIVE_DATA_DIALOG"
    }
}
