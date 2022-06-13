package com.example.HCI_Project

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun chatlist(navController: NavController){
    Column {
        Button(onClick = { navController.navigate("assistant") },Modifier.padding(7.dp)) {
            Text(text = "< Back")
        }
        LazyColumn{items(VoiceTTS.responce_list){text ->oneList(text)} }

    }

}

@Composable
fun oneList(text:String){
    var fst:Char=text[0]
    if(fst=='#'){
        assistantList(text.substring(1))
    }else if(fst=='$'){
        speakerList(text.substring(1))
    }
}

@Composable
fun assistantList(text:String){
    Row(Modifier.padding(15.dp)) {
        Image(
            painter = painterResource(R.drawable.head_sculpture1),
            contentDescription = "head_sculpture1",
            modifier = Modifier
                // Set image size to 40 dp
                .size(50.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)

        )
        Spacer(modifier = Modifier.width(9.dp))
        Column {
            Text(text = "Assistant", color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Surface(shape = RoundedCornerShape(10), tonalElevation = 1.dp,shadowElevation=1.dp) {
                Text(
                    text = text,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
            }

        }
    }
}

@Composable
fun speakerList(text:String){
        Row(Modifier.padding(15.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "User", color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(shape = RoundedCornerShape(10), tonalElevation = 1.dp,shadowElevation=1.dp, color = Color(
                    0xFFE6F7FF
                )
                ) {
                    Text(
                        text = text,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp
                    )
                }

            }
            Spacer(modifier = Modifier.width(9.dp))
            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = "user",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(50.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)

            )
        }

}