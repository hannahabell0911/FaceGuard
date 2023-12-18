package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders
import androidx.room.Entity
import androidx.room.PrimaryKey



data class Reminder(
    val date: String,     // Expected format: "yyyy-MM-dd"
    val time: String,     // Expected format: "HH:mm"
    val name: String,
    val workType: String
) {
    val hour: Int
        get() = time.split(":").firstOrNull()?.toIntOrNull() ?: 0

    val minute: Int
        get() = time.split(":").lastOrNull()?.toIntOrNull() ?: 0
}
