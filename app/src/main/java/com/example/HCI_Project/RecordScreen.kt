package com.example.HCI_Project

import android.annotation.SuppressLint
import android.text.Layout
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.view.View
import android.widget.GridLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import jp.live2d.sample.LAppLive2DManager
import jp.live2d.sample.LAppLive2DManager.TAG


@SuppressLint("UnrememberedMutableState")
@Composable
fun RecordBoard(
    viewModel: AsrViewModel,l2dview: View,l2dmanager: LAppLive2DManager,navController: NavController
) {

    val text = viewModel.text.observeAsState()
    var state_text by mutableStateOf("start")
    val code = viewModel.code.observeAsState()
    val launcher = rememberLauncherForActivityResult(UserContracts()) {
        Log.i(TAG, "init launcher")
    }
    when(code.value) {
        "0" -> {}
        "1" -> {
            // 通讯录
            launcher.launch("1")
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Surface(shape = RectangleShape,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()) {
            Row() {
                text.value?.let { Greeting(it) }
                Button(
                    onClick = {
                        l2dmanager.changeModel()
                    },
                    ) {
                    Text(text = "change")
                }
                Button(
                    onClick = { navController.navigate("chatlist") },
                    ) {
                    Text(text = "chatpage")
                }
            }
        }

        Button(onClick = { viewModel.changePattern() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(20.dp, -20.dp)
                .clip(CircleShape)
                .size(100.dp)
        ) {
            Text(text = "changePattern")
        }
        Button(onClick = {
            if (state_text == "start") {
                println("start")
                viewModel.start()
                state_text = "stop"
                VoiceTTS.stop()
            } else if (state_text == "stop") {
                println("stop")

                viewModel.stop()
                state_text = "start"
                //说完话增加模拟点击
                SimulateTouch.simulateTouchEvent(
                    l2dview,
                    l2dview.width / 2f,
                    l2dview.height / 2f
                )
            }
        },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(-20.dp, -20.dp)
                .clip(CircleShape)
                .size(80.dp)) {
            Text(text = state_text)
        }
    }

}
