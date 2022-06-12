package com.example.HCI_Project

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface

@Composable
fun chatlist(navController: NavController){
    Column {
        Button(onClick = { navController.navigate("assistant") }) {
            Text(text = "< Back")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.head_sculpture1),
                contentDescription = "head_sculpture1",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "ass", color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = RoundedCornerShape(2), tonalElevation = 1.dp,shadowElevation=1.dp) {
                    Text(
                        text = "text",
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }

    }


}
