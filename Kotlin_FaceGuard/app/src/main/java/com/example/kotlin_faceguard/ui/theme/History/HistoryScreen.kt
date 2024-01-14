package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.History

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.example.kotlin_faceguard.R


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(navController: NavHostController) {
    Log.d("History Screen ", "Composing History")
    var filterOption by remember { mutableStateOf("Today") }
    val historyItems = listOf(
        HistoryItem("John Loane", "Dec 2, 2023, 9:00 AM", true, R.drawable.hannah),
        HistoryItem("John Loane", "Dec 2, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Loane", "Dec 2, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Loane", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("Bride", "Dec 1, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Dec 2, 2023, 10:00 AM", true, R.drawable.newimage),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("Bride", "Nov 28, 2023, 10:00 AM", true, R.drawable.newimage),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("Bride", "Dec 1, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.newimage),
        HistoryItem("John Loane", "Dec 2, 2023, 9:00 AM", true, R.drawable.hannah),
        HistoryItem("John Loane", "Dec 2, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Loane", "Dec 2, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Loane", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("Bride", "Dec 1, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Dec 2, 2023, 10:00 AM", true, R.drawable.newimage),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("Bride", "Nov 28, 2023, 10:00 AM", true, R.drawable.newimage),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("Bride", "Dec 1, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.newimage),


        )
    val filteredItems = when (filterOption) {
        "Today" -> historyItems.filter { it.dateTime.isToday() }
        "Yesterday" -> historyItems.filter { it.dateTime.isYesterday() }
        "Last Week" -> historyItems.filter { it.dateTime.isLastWeek() }
        else -> historyItems
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    // Wrap Text in Row for center alignment
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "FaceGuard",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            )
        },
        backgroundColor = Color.Gray // bg color
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("History", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                DropdownMenuExample(filterOption) { selectedOption ->
                    filterOption = selectedOption
                }
            }
            LazyColumn {
                items(filteredItems) { item ->
                    HistoryCard(item)
                }
            }
        }
    }
}

@Composable
fun DropdownMenuExample(selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Today", "Yesterday", "Last Week")

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Text(selectedOption, modifier = Modifier.clickable { expanded = true })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onOptionSelected(option)
                }) {
                    Text(option)
                }
            }
        }
    }
}

// Dummy extension functions for date checks, replace with your actual logic
fun String.isToday(): Boolean = this.contains("Dec 2, 2023") // Replace with actual logic
fun String.isYesterday(): Boolean = this.contains("Nov 30, 2023") // Replace with actual logic
fun String.isLastWeek(): Boolean = this.contains("Nov") // Replace with actual logic


@Composable
fun HistoryCard(historyItem: HistoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            Image(
                painter = painterResource(id = historyItem.imageResId),
                contentDescription = "Photo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            // Middle content (Name and Known/Unknown label)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = historyItem.name, fontWeight = FontWeight.Bold)
                Text(text = if (historyItem.isKnown) "Known" else "Unknown")
            }

            // Date and time
            Text(text = historyItem.dateTime, textAlign = TextAlign.End)
        }
    }
}


data class HistoryItem(
    val name: String,
    val dateTime: String,
    val isKnown: Boolean,
    val imageResId: Int
)