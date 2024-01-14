package com.example.kotlin_faceguard.ui

import android.annotation.SuppressLint
import android.app.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.*
import android.os.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yourpackage.api.com.example.kotlin_faceguard.ReminderBroadcastReceiver
import com.yourpackage.api.com.example.kotlin_faceguard.ReminderViewModel
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders.Reminder
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
    var successMessage by remember { mutableStateOf<String?>(null) }
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
    @SuppressLint("ScheduleExactAlarm")
    fun createReminder() {
        if (selectedDate.isBlank() || name.isBlank() || selectedWorkType.isBlank()) {
            successMessage = "Please fill all fields"
            return
        }
        try {
            val reminderTime = "$selectedHour:$selectedMinute"
            val reminder = Reminder(selectedDate, reminderTime, name, selectedWorkType)
            viewModel.setReminder(reminder)
            scheduleReminder(context, reminder)

            showSnackbar = true
            successMessage = "Reminder Created Successfully"
            navController.popBackStack() // Go back after setting the reminder
        } catch (e: Exception) {
            successMessage = "Error creating reminder: ${e.message}"
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        "FaceGuard",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp
                    )
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Create a Reminder",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Select Type of Work", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                WorkTypeDropdown(selectedWorkType = selectedWorkType, onWorkTypeSelected = { selectedWorkType = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Date and Time", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
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

    successMessage?.let {
        AlertDialog(
            onDismissRequest = { successMessage = null },
            title = { Text("Successfully added a Reminder") },
//            text = { Text(it) },
            confirmButton = {
                Button(onClick = { successMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
}

fun showDatePicker(context: Context, selectedDate: MutableState<String>) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

// Function to show time picker
fun showTimePicker(context: Context, selectedHour: MutableState<Int>, selectedMinute: MutableState<Int>) {
    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedHour.value = hourOfDay
            selectedMinute.value = minute
        },
        selectedHour.value,
        selectedMinute.value,
        true // 'true' for 24-hour view
    ).show()
}

// Function to handle reminder creation
fun createReminder(context: Context, selectedDate: String, selectedHour: Int, selectedMinute: Int, name: String, selectedWorkType: String, viewModel: ReminderViewModel, showSnackbar: MutableState<Boolean>) {
    if (selectedDate.isBlank() || name.isBlank() || selectedWorkType.isBlank()) {
        // Handle error
        return
    }
    try {
        val reminderTime = "$selectedHour:$selectedMinute"
        val reminder = Reminder(selectedDate, reminderTime, name, selectedWorkType)
        viewModel.setReminder(reminder)
        scheduleReminder(context, reminder)

        showSnackbar.value = true
        // Pop back stack...
    } catch (e: Exception) {
        // Handle exception
    }
}

@Composable
fun WorkTypeDropdown(selectedWorkType: String, onWorkTypeSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val workTypes = listOf("Business", "Personal", "Other")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(onClick = { expanded = true }) {
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

@SuppressLint("ScheduleExactAlarm")
fun scheduleReminder(context: Context, reminder: Reminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderBroadcastReceiver::class.java)

    val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, pendingIntentFlags)

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, reminder.hour)
        set(Calendar.MINUTE, reminder.minute)
    }

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}
