package com.example.HCI_Project

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.baidu.speech.asr.SpeechConstant
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.baidu.speech.EventListener
import jp.live2d.sample.LAppLive2DManager


@SuppressLint("UnrememberedMutableState")
@Composable
fun RecordBoard(
    viewModel: AsrViewModel,live2DMgr: LAppLive2DManager
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
            }
        }){
                Text(text =state_text)
        }
//        Button(onClick = {
//            println("speak")
//            VoiceTTS.start("你好")
//        }) {
//            Text(text = "speak")
//        }
    }

}