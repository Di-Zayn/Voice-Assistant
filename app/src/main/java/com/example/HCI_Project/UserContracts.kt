package com.example.HCI_Project

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContract
import java.net.URLEncoder

class UserContracts : ActivityResultContract<MutableMap<String, String?>?, Uri?>() {

    private lateinit var intent: Intent

    override fun createIntent(context: Context, input: MutableMap<String, String?>?): Intent {
        when(input?.get("code")) {
            "1" -> {
                intent = Intent(Intent.ACTION_PICK).setType(ContactsContract.Contacts.CONTENT_TYPE)
            }
            "2" -> {
                val uri = Uri.parse("http://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" +
                        URLEncoder.encode(input["obj"],"UTF-8"))
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
            }
            "3" -> {
                intent = Intent()
                val componentName =  ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                intent.action = Intent.ACTION_MAIN
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.component =componentName
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