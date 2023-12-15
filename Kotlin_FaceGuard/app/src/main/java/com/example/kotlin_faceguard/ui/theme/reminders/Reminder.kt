package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders
import androidx.room.Entity
import androidx.room.PrimaryKey



data class Reminder(
    val date: String,
    val time: String,
    val name: String,
    val workType: String
)
