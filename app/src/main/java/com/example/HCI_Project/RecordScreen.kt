package com.example.HCI_Project

import android.annotation.SuppressLint
import android.text.Layout
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import android.view.View
import android.widget.GridLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.HCI_Project.ui.theme.Pink80
import com.example.HCI_Project.ui.theme.PurpleGrey80
import jp.live2d.sample.LAppLive2DManager
import jp.live2d.sample.LAppLive2DManager.TAG


@SuppressLint("UnrememberedMutableState")
@Composable
fun RecordBoard(
    viewModel: AsrViewModel,l2dview: View,l2dmanager: LAppLive2DManager,navController: NavController
) {

    val text = viewModel.text2.observeAsState()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val size by animateDpAsState(if (isPressed) 36.dp else 24.dp)
    var show by remember { mutableStateOf(true) }

    var response by remember { mutableStateOf(true) }

    var click1 by remember { mutableStateOf(true) }
    val rotate1 by animateFloatAsState(if (click1) 360f else 180f)

    var click2 by remember { mutableStateOf(true) }
    val rotate2 by animateFloatAsState(if (click2) 360f else 180f)

    val code = viewModel.code.observeAsState()
    val launcher = rememberLauncherForActivityResult(UserContracts()) {
        Log.i(TAG, "init launcher")
        viewModel.resetCode()
    }
    when(code.value) {
        "-1" -> {}
        "0" -> {}
        else -> {
            val map = mutableMapOf<String, String?>()
            code.value?.let {
                map["code"] = code.value
            }
            map["obj"] = viewModel.getIntentObj()
            launcher.launch(map)
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
                .padding(10.dp)
                .height(60.dp)
                .fillMaxWidth()) {
//           Box() {
                //text.value?.let { Greeting(it) }
               IconButton(
                   onClick = { viewModel.changePattern()
                       click2 = !click2},
                   modifier = Modifier
                       .align(Alignment.CenterStart)
//                       .clip(CircleShape)
//                       .size(100.dp)
               ) {
                   if(click2){
                       Icon(imageVector = Icons.Outlined.ContactSupport, contentDescription = "changePattern")
                   }
                   else {
                       Icon(imageVector = Icons.Outlined.Launch, contentDescription = "changePattern")
                   }
                   Icon(imageVector = Icons.Sharp.Autorenew, contentDescription = "", Modifier.size(60.dp).rotate(rotate2))

                   //Text(text = "changePattern")
               }
               IconButton(
                    onClick = {
                        l2dmanager.changeModel()
                        click1=!click1 },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(imageVector = Icons.Filled.Face, contentDescription = "change")
                    Icon(imageVector = Icons.Sharp.Autorenew, contentDescription = "",
                        Modifier.requiredSize(60.dp)
                            .rotate(rotate1))
                    //Text(text = "change")
                }
                IconButton(
                    onClick = { navController.navigate("chatlist") },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(imageVector = Icons.Outlined.Forum, contentDescription = "chatpage", Modifier.requiredSize(40.dp))
                    //Text(text = "chatpage")
                }
//            }
        }
//        AnimatedVisibility(
//            visible = show,
//            enter = fadeIn(initialAlpha = 0.9f),
//            exit = slideOutVertically(
//                // Exits by sliding up from offset 0 to -fullHeight.
//                targetOffsetY = { fullHeight -> -fullHeight },
//                animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
//            )+ fadeOut()
//        ) {
            Surface(
                shape = RoundedCornerShape(10),
                tonalElevation = 1.dp, shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.Center)
                    .offset(y = -230.dp)
                    .padding(5.dp)
            ) {

                text.value?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(all = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp
                    )
                }
            //}
        }
        IconButton(
            interactionSource = interactionSource,
            onClick={},
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(- size * 1.5f + 20.dp + 40.dp, size * 1.5f - 20.dp - 40.dp)
                .clip(CircleShape)
                .size(size * 3)
                .background(PurpleGrey80)
        ) {
            if(isPressed){
                viewModel.start()
                VoiceTTS.stop()
                show=false
            }
            else{
                viewModel.stop()
                //说完话增加模拟点击
                SimulateTouch.simulateTouchEvent(
                    l2dview,
                    l2dview.width / 2f,
                    l2dview.height / 2f
                )
                show=true
            }
            Icon(
                imageVector = Icons.Outlined.Mic,
                contentDescription = "",
                Modifier.size(size * 2)
            )
            //Text(text = state_text)
        }

    }

}
