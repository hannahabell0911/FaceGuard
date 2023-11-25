package com.example.faceguardpages

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.faceguardpages.Data.data
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RecognizedFaces : ComponentActivity() {

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.faceguard.live/") // Update with your actual API URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces)

        val composeView = findViewById<ComposeView>(R.id.composeView)

        composeView.setContent {
            FacesScreen()
        }
        val recognizedFacesIcon = findViewById<ImageView>(R.id.home)

        // Set an OnClickListener to handle icon click
        recognizedFacesIcon.setOnClickListener {
            // Create an Intent to start RecognizedFacesActivity
            val intent = Intent(this, MainActivity::class.java)

            // Start the RecognizedFacesActivity
            startActivity(intent)

            lifecycleScope.launch {
                try {
                    val knownFaces = apiService.getKnownFaces()
                } catch (e: Exception) {
                    // Handle errors
                }
            }
        }
    }
    interface ApiService {
        @GET("known-faces")
        suspend fun getKnownFaces(): List<data.KnownFace>

    }
    @Composable
    fun FacesScreen() {
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

