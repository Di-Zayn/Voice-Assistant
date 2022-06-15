package com.example.HCI_Project

import android.Manifest
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.HCI_Project.ui.theme.MyApplicationTheme
import jp.live2d.sample.LAppLive2DManager

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
            Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS))

        val asr = AsrViewModel(this) //如何获取上下文来着？ 以及如何定义一个全局的上下文
        VoiceTTS.initTTS(this)


        // ui
        setContent {
            MyApplicationTheme {
                MyApp(asr = asr)
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}

@Composable
fun MyApp(asr:AsrViewModel) {

    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = {
            shouldShowOnboarding = false
            VoiceTTS.start("你好，有什么可以帮助您的？")
        })
    } else {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = "assistant",
            route = "root"
        ) {
            composable("assistant") {
                live2d(asr,navController)
            }
            composable("chatlist") {
                chatlist(asr,navController)
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(painter = painterResource(id = R.drawable.voice_assistant),
                contentDescription = "voice",
                modifier = Modifier
                    .size(150.dp, 150.dp))
            Spacer(modifier = Modifier.height(50.dp))
            Text("Welcome to the Voice Assistant!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(70.dp))
            Text("Tips:")
            Text("1.In the default mode, you can chat with the voice assistant",
                modifier = Modifier.width(300.dp))
            Text("2.Or you can switch modes to let the voice assistant launch other app for you",
                modifier = Modifier.width(300.dp))
            Text("3.Your chats with the voice assistant are stored in the history of conversations, which can be viewed by clicking on the history",
                modifier = Modifier.width(300.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    MyApplicationTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}



//@Preview(showBackground = true, widthDp = 320)
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        Greetings()
//    }
//}