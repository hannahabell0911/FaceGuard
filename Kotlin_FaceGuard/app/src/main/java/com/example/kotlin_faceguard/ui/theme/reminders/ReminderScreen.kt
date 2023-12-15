package com.example.kotlin_faceguard.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders.Reminder
import com.yourpackage.api.com.example.kotlin_faceguard.ReminderViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReminderScreen(navController: NavController, viewModel: ReminderViewModel) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var selectedWorkType by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    // Function to show date picker
    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Function to show time picker
    fun showTimePicker() {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                selectedHour = hourOfDay
                selectedMinute = minute
            },
            selectedHour,
            selectedMinute,
            true // 'true' for 24-hour view
        ).show()
    }

    // Function to handle reminder creation
    fun createReminder() {
        if (selectedDate.isBlank() || name.isBlank() || selectedWorkType.isBlank()) {
            errorMessage = "Please fill all fields"
            return
        }
        try {
            val reminderTime = "$selectedHour:$selectedMinute"
            val reminder = Reminder(selectedDate, reminderTime, name, selectedWorkType)
            viewModel.setReminder(reminder)
            showSnackbar = true
            navController.popBackStack() // Go back after setting the reminder
        } catch (e: Exception) {
            errorMessage = "Error creating reminder: ${e.message}"
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Schedule Arrival") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            WorkTypeDropdown(selectedWorkType = selectedWorkType, onWorkTypeSelected = { selectedWorkType = it })
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                OutlinedButton(onClick = { showDatePicker() }) {
                    Text(if (selectedDate.isEmpty()) "Select Date" else selectedDate)
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = { showTimePicker() }) {
                    Text("${selectedHour.toString().padStart(2, '0')}:${selectedMinute.toString().padStart(2, '0')}")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { createReminder() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Set Reminder")
            }
        }
    }

    if (showSnackbar) {
        LaunchedEffect(key1 = Unit) {
            scaffoldState.snackbarHostState.showSnackbar("Reminder Created Successfully")
            showSnackbar = false
        }
    }

    errorMessage?.let {
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            title = { Text("Error") },
            text = { Text(it) },
            confirmButton = {
                Button(onClick = { errorMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
}
@Composable
fun WorkTypeDropdown(selectedWorkType: String, onWorkTypeSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val workTypes = listOf("Business", "Personal", "Other")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (selectedWorkType.isEmpty()) "Select Work Type" else selectedWorkType)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            workTypes.forEach { workType ->
                DropdownMenuItem(onClick = {
                    onWorkTypeSelected(workType)
                    expanded = false
                }) {
                    Text(workType)
                }
            }
        }
    }
}
