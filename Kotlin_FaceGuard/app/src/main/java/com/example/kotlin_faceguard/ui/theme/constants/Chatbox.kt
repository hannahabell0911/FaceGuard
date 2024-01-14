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
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.platform.LocalContext

import com.pubnub.api.PubNub
import com.yourpackage.api.com.example.kotlin_faceguard.MainActivity

import androidx.compose.material.*

import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun ChatMessageBox(
    modifier: Modifier = Modifier,
    channelName: String,
    pubnub: PubNub,
    onMessageSent: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var messageSent by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun sendMessageToPubNub(message: String) {
        // Ensure that this function does not contain @Composable invocations
        val messagePayload = mapOf("text" to message, "userId" to "myUserId") // Replace "myUserId" with the actual user ID

        (context as MainActivity).sendMessageToPubNub(channelName, messagePayload) { success ->
            messageSent = success
        }
    }

    LaunchedEffect(messageSent) {
        if (messageSent) {
            delay(3000) // Delay for 3 seconds
            messageSent = false
            text = "" // Reset the text field
            onMessageSent() // Notify the caller that the message has been sent
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    if (!messageSent) MaterialTheme.colors.surface
                    else MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Type a message") },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    sendMessageToPubNub(text)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        if (messageSent) {
            Text(
                "Message Sent",
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
