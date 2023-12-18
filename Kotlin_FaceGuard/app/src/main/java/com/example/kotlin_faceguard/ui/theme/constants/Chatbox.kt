package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.constants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun ChatMessageBox(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var messageSent by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White, shape = MaterialTheme.shapes.small.copy(CornerSize(12.dp)))
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    messageSent = false
                },
                placeholder = {
                    if (text.isEmpty()) Text("Send message at the Door", color = Color.Gray)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(onClick = {
                // Handle send
                messageSent = true
                text = ""
                // Optional: Add logic to actually send the message
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message",
                    tint = Color.Black
                )
            }
        }
        if (messageSent) {
            // Display the success message
            Text("Message sent to the door successfully", color = Color.Green, fontSize = 16.sp)
            LaunchedEffect(key1 = messageSent) {
                delay(3000) // Message is shown for 3 seconds
                messageSent = false
            }
        }
    }
}