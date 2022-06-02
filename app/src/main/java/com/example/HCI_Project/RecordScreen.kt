package com.example.HCI_Project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.baidu.speech.asr.SpeechConstant
import androidx.compose.runtime.livedata.observeAsState
import com.baidu.speech.EventListener


@Composable
fun RecordBoard(
    viewModel: AsrViewModel,
) {

    val text = viewModel.text.observeAsState()


    Column {
        text.value?.let { Greeting(it) }
        Button(onClick = {
            println("start")
            viewModel.start()
        }) {
            Text(text = "Start")
        }
        Button(onClick = {
            println("stop")
            viewModel.stop()
        }) {
            Text(text = "Stop")
        }
    }

}