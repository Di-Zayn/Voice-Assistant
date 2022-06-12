package com.example.HCI_Project

import android.Manifest
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.HCI_Project.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

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
        VoiceTTS.initTTS(this)


        // ui
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = "assistant",
                    route = "root"
                ) {
                    composable("assistant") {
                        live2d(asr,navController)
                    }
                    composable("chatlist") {
                        chatlist(navController)
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