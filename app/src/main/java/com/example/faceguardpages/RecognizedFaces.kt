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
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.reflect.Type

data class EventLogEntry(val name: String, val timestamp: String)

class RecognizedFaces : ComponentActivity() {

    private val TAG = "RecognizedFacesActivity"
    private val gson = GsonBuilder()
        .registerTypeAdapter(EventLogEntry::class.java, EventLogEntryDeserializer())
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.faceguard.live/")
        .addConverterFactory(GsonConverterFactory.create(gson))
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
            Log.d(TAG, "Returning to MainActivity.")
        }
    }

    interface ApiService {
        @GET("/recognized_guests")
        suspend fun getRecognizedGuests(): Response<List<List<String>>>

        @GET("/event_log")
        suspend fun getEventLog(): Response<List<EventLogEntry>>
    }

    @Composable
    fun FacesScreen(apiService: ApiService) {
        var recognizedGuests = remember { mutableStateOf<List<String>?>(null) }
        var eventLog = remember { mutableStateOf<List<EventLogEntry>?>(null) }
        val coroutineScope = rememberCoroutineScope()

        suspend fun fetchData() {
            coroutineScope {
                launch {
                    fetchRecognizedGuests(apiService, recognizedGuests)
                }
                launch {
                    fetchEventLog(apiService, eventLog)
                }
            }
        }

        LaunchedEffect(Unit) {
            fetchData()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    fetchData()
                }
            }) {
                Text("Fetch Data")
            }

            Text("Recognized Guests:")
            recognizedGuests.value?.let { names: List<String> ->
                Text(names.joinToString(", "))
            }

            Text("Event Log:")
            eventLog.value?.let { logs ->
                logs.forEach { log ->
                    Text("${log.name} at ${log.timestamp}")
                }
            }
        }
    }

    private suspend fun fetchRecognizedGuests(apiService: ApiService, recognizedGuestsState: MutableState<List<String>?>) {
        try {
            val response = apiService.getRecognizedGuests()
            if (response.isSuccessful) {
                recognizedGuestsState.value = response.body()?.flatten()
            } else {
                Log.e(TAG, "Error fetching recognized guests: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in fetching recognized guests", e)
        }
    }

    private suspend fun fetchEventLog(apiService: ApiService, eventLogState: MutableState<List<EventLogEntry>?>) {
        try {
            val response = apiService.getEventLog()
            if (response.isSuccessful) {
                eventLogState.value = response.body()
            } else {
                Log.e(TAG, "Error fetching event log: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in fetching event log", e)
        }
    }

    class EventLogEntryDeserializer : JsonDeserializer<EventLogEntry> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EventLogEntry {
            val jsonArray = json.asJsonArray
            val name = jsonArray.get(0).asString
            val timestamp = jsonArray.get(1).asString
            return EventLogEntry(name, timestamp)
        }
    }
}
