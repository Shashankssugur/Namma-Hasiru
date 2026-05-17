package com.nammahasiru

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nammahasiru.data.AppContainer
import com.nammahasiru.reminders.PlantStatusReminderWorker
import java.util.concurrent.TimeUnit

class NammaHasiruApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        createReminderNotificationChannel()
        scheduleReminderWorker()
    }

    private fun createReminderNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            REMINDER_CHANNEL_ID,
            getString(R.string.notif_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notif_channel_desc)
        }

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    /**
     * Runs once per day to find plants older than 90 days and remind the user.
     * (The app also works without network, so we explicitly do NOT require it.)
     */
    private fun scheduleReminderWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val work = PeriodicWorkRequestBuilder<PlantStatusReminderWorker>(
            1,
            TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            REMINDER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
    }

    companion object {
        const val REMINDER_CHANNEL_ID = "plant_reminders"
        const val REMINDER_WORK_NAME = "plant_status_reminder_daily"
    }
}

