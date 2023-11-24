
package com.example.faceguardpages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.faceguardpages.ui.theme.FaceGuardPagesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeView = findViewById<ComposeView>(R.id.composeView)

       setContent {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    // Your Compose-based home screen content goes here
    Surface(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Compose!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            // Add more Compose components as needed
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    FaceGuardPagesTheme {
       HomeScreen()
    }
}