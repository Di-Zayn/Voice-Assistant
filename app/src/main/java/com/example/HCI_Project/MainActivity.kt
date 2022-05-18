package com.example.HCI_Project

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.example.HCI_Project.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    // 能否建一个单例作为全局的listener
//    private val listener = object: EventListener {
//
//        private var asr: EventManager = EventManagerFactory.create(this@MainActivity, "asr")
//
//        init {
//            asr.registerListener(this)
//        }
//
//        override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
//            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
//                // 识别相关的结果都在这里
//                if (params == null || params.isEmpty()) {
//                    return
//                }
//                if (params.contains("\"final_result\"")) {
//                    // 一句话的最终识别结果
//                    GlobalViewModel.setText(params)
//                    // 用global view model 存储
//                }
//            }
//        }
//
//        fun start() {
//            asr.send(SpeechConstant.ASR_START, "{}", null, 0, 0)
//        }
//
//        fun stop() {
//            asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
//        }
//
//        fun destroy() {
//            //发送取消事件
//            asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
//            //退出事件管理器
//            // 必须与registerListener成对出现，否则可能造成内存泄露
//            asr.unregisterListener(this);
//        }
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 申请权限
        val permission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                // true: 用户同意   false：用户不同意 or 用户不处理
                for(i in it){
                    if(i.value) Toast.makeText(this, "SUCCESSFUL", Toast.LENGTH_LONG).show()
                }
            }
        permission.launch(arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE))

        val asr = AsrViewModel(this) //如何获取上下文来着？ 以及如何定义一个全局的上下文

        // ui
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = "home",
                    route = "root"
                ) {
                    composable("home") {
                        RecordScreen(asr)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}