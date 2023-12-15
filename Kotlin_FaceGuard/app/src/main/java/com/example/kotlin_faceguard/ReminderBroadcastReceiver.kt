package com.yourpackage.api.com.example.kotlin_faceguard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.NotificationChannel
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.kotlin_faceguard.R


import kotlin.random.Random

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Create a notification channel
        createNotificationChannel(context)

        // Build a notification
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.faceguard_icon) // Replace with your own icon
            .setContentTitle("Reminder Alert")
            .setContentText("You have a new reminder.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Optional: Large icon or image
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.faceguard_icon)
        notificationBuilder.setLargeIcon(largeIcon)

        // Notification ID
        val notificationId = Random.nextInt()

        // Show the notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder Notifications"
            val descriptionText = "Notifications for Reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "reminder_channel_id"
        const val ACTION_REMINDER = "com.yourpackage.api.com.example.kotlin_faceguard.ACTION_REMINDER"
    }
}
