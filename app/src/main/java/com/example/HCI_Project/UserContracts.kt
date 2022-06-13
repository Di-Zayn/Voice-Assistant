package com.example.HCI_Project

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContract

class UserContracts : ActivityResultContract<String?, Uri?>() {

    private lateinit var intent: Intent

    override fun createIntent(context: Context, input: String?): Intent {
        when(input) {
            "1" -> {
                intent = Intent(Intent.ACTION_PICK).setType(ContactsContract.Contacts.CONTENT_TYPE)
            }
        }
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode == Activity.RESULT_OK) {
            return null
        }
        return null
    }

}