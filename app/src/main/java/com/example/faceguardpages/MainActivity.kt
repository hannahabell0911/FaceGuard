
package com.example.faceguardpages

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val composeView = findViewById<ComposeView>(R.id.composeView)

        composeView.setContent {
            HomeScreen()
        }

        val recognizedFacesIcon = findViewById<ImageView>(R.id.faces)

        // Set an OnClickListener to handle icon click
        recognizedFacesIcon.setOnClickListener {
            // Create an Intent to start RecognizedFacesActivity
            val intent = Intent(this, RecognizedFaces::class.java)

            // Start the RecognizedFacesActivity
            startActivity(intent)
        }
    }

        @Composable
        fun HomeScreen() {
            Surface(
                color = Color.Gray,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Add more Compose components as needed
                }
            }
        }
    }



