package com.example.faceguardpages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import retrofit2.Response
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
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.faceguardpages.Data.data
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RecognizedFaces : ComponentActivity() {

    private val YAG = "RecognizedFacesActivity"
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.faceguard.live/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces)

        val composeView = findViewById<ComposeView>(R.id.composeView)

        composeView.setContent {
            FacesScreen(apiService)
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
                    val recognizedGuests = apiService.getRecognizedGuests()
                } catch (e: Exception) {
                    Log.e(YAG, "Error fetching recognized guests", e)
                }
            }
        }
    }

    interface ApiService {
        @GET("/recognized_guests")
        suspend fun getRecognizedGuests(): Response<data.RecognizedGuest>
    }

    @Composable
    fun FacesScreen(apiService: ApiService) {
        var recognizedGuests by remember { mutableStateOf<List<String>?>(null) }


        LaunchedEffect(Unit) {
            try {
                // Uncomment the line below when you want to make the actual API call
                val response = apiService.getRecognizedGuests()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        recognizedGuests = body.names
                        Log.d(YAG, "Received recognized guests: $recognizedGuests")
                    } else {
                        Log.e(YAG, "Response body is null")
                    }
                } else {
                    Log.e(YAG, "Unsuccessful response: ${response.code()}, ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle errors
                Log.e(YAG, "Error in LaunchedEffect", e)
            }
        }


        // Rest of the code...
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),  // Add a background color for debugging
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Recognized Guests:")
            recognizedGuests?.let { names ->
                // Directly use the list of names in the Text composable
                Text(names.joinToString(", "))
            }
        }
        }
    }
