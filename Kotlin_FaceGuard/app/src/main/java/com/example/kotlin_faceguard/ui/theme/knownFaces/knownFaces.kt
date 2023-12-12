package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.knownFaces

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.yourpackage.api.RetrofitService
import com.yourpackage.api.com.example.kotlin_faceguard.KnownFace
import kotlinx.coroutines.launch


class KnownFacesViewModel : ViewModel() {
    private val _knownFaces = MutableLiveData<List<KnownFace>>()
    val knownFaces: LiveData<List<KnownFace>> = _knownFaces

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchKnownFaces()
    }

    private fun fetchKnownFaces() {
        viewModelScope.launch {
            try {
                Log.d("KnownFacesViewModel", "Attempting to fetch known faces")
                val faces = RetrofitService.apiService.getKnownFaces()
                _knownFaces.value = faces
                Log.d("KnownFacesViewModel", "Fetched faces: ${faces.size}")
            } catch (e: Exception) {
                _error.value = "Failed to fetch known faces"
                Log.e("KnownFacesViewModel", "Error fetching known faces", e)
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun KnownFacesScreen(navController: NavController, viewModel: KnownFacesViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Back button
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        // Spacer to push the title to the center
                        Spacer(modifier = Modifier.weight(0.55f))
                        // Title
                        Text(
                            "Known Faces",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                        // Another spacer to balance the layout
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            )
        },
        backgroundColor = Color.Gray // Set the background color for Scaffold
    ) {
        val knownFaces by viewModel.knownFaces.observeAsState(initial = emptyList())

        LazyColumn {
            items(knownFaces) { face ->
                KnownFaceCard(face)
            }
        }
    }
}

@Composable
fun KnownFaceCard(face: KnownFace) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp) // Match the rounded corners from HistoryCard
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            Image(
                painter = rememberImagePainter(face.imageUrl),
                contentDescription = "Face Image",
                modifier = Modifier
                    .size(40.dp) // Adjusted to match the size in HistoryCard
                    .clip(CircleShape)
            )

            // Middle content (Name, Relation, and Date)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = face.name, fontWeight = FontWeight.Bold) // Name
                Text(text = "Relation: ${face.relation}") // Relation
                Text(text = "Date: ${face.date}") // Date
            }
        }
    }
}


