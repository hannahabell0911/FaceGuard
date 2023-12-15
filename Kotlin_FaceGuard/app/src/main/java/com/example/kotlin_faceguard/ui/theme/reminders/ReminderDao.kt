package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders

import androidx.room.Dao
import androidx.room.Insert


import androidx.room.Query

@Dao
interface ReminderDAO {

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Query("SELECT * FROM Reminder")
    suspend fun getAllReminders(): List<Reminder>

    // Add additional queries as needed, e.g., delete, update, etc.
}
