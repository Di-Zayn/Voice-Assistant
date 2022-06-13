package com.example.HCI_Project

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import jp.live2d.sample.LAppLive2DManager
import jp.live2d.utils.android.FileManager
import jp.live2d.utils.android.SoundManager

@SuppressLint("UnrememberedMutableState")
@Composable
fun live2d(
    viewModel: AsrViewModel,navController: NavController
) {

    val context = LocalContext.current
    val live2DMgr = LAppLive2DManager()
    SoundManager.init(context)
    val view:View = live2DMgr.createView(context as Activity?)
    val layout = remember {
        FrameLayout(context).apply {
            id = R.id.live2DLayout
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = {layout}) {
            layout.addView(
                view,
                0,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        RecordBoard(viewModel,view,live2DMgr,navController)

    }

    FileManager.init(context)

}

