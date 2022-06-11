package com.example.HCI_Project

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.view.View
import jp.live2d.sample.LAppLive2DManager


@SuppressLint("UnrememberedMutableState")
@Composable
fun RecordBoard(
    viewModel: AsrViewModel,l2dview: View,l2dmanager: LAppLive2DManager
) {

    val text = viewModel.text.observeAsState()
    var state_text by mutableStateOf("start")
    Column {
        text.value?.let { Greeting(it) }
        Button(onClick = {
            if(state_text=="start"){
                println("start")
                viewModel.start()
                state_text="stop"
                VoiceTTS.stop()
            }
            else if(state_text=="stop") {
                println("stop")

                viewModel.stop()
                state_text="start"
                VoiceTTS.start("你好")
                //说完话增加模拟点击
                SimulateTouch.simulateTouchEvent(l2dview, l2dview.width/2f,l2dview.height/2f)
            }
        }){
                Text(text =state_text)
        }
        Button(onClick = { l2dmanager.changeModel() }) {
            Text(text = "change")
        }
    }

}