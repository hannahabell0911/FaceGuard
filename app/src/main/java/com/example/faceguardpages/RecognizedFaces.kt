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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RecognizedFaces : ComponentActivity() {

    private val TAG = "RecognizedFacesActivity"
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
        recognizedFacesIcon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d("sunucu", "Returning to MainActivity.")
        }
    }

    interface ApiService {
        @GET("/recognized_guests")
        suspend fun getRecognizedGuests(): Response<List<List<String>>>
    }

    @Composable
    fun FacesScreen(apiService: ApiService) {
        var recognizedGuests by remember { mutableStateOf<List<String>?>(null) }

        LaunchedEffect(Unit) {
            try {
                Log.d("sunucu", "Attempting to fetch recognized guests.")
                val response = apiService.getRecognizedGuests()
                Log.d("sunucu", "Response received: ${response.raw()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        recognizedGuests = body.flatten()
                        Log.d("sunucu", "Received recognized guests: $recognizedGuests")
                    } else {
                        Log.e("sunucu", "Response body is null")
                    }
                } else {
                    Log.e("sunucu", "Unsuccessful response: ${response.code()}, ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("sunucu", "Error in LaunchedEffect", e)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Recognized Guests:")
            recognizedGuests?.let { names ->
                Text(names.joinToString(", "))
            }
        }
    }
}
