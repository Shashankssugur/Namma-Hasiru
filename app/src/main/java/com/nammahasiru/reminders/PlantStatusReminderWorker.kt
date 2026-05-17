package com.nammahasiru.reminders

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nammahasiru.NammaHasiruApp
import com.nammahasiru.MainActivity
import com.nammahasiru.R
import com.nammahasiru.data.PlantStatus
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class PlantStatusReminderWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as? NammaHasiruApp ?: return Result.failure()
        val repo = app.container.plants

        val candidates = repo.unknownUnreminded()
        val today = LocalDate.now()

        // Remind if any plant is older than or equal to 90 days and still UNKNOWN.
        val oldEnough = candidates.filter { plant ->
            if (plant.status != PlantStatus.UNKNOWN) return@filter false
            val planted = LocalDate.ofEpochDay(plant.datePlantedEpochDay)
            ChronoUnit.DAYS.between(planted, today) >= 90
        }

        if (oldEnough.isEmpty()) return Result.success()

        // Update DB (mark reminderSent) BEFORE notifying to avoid duplicates.
        oldEnough.forEach { plant ->
            repo.update(plant.copy(reminderSent = true, lastUpdatedAtMillis = System.currentTimeMillis()))
        }

        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val manager = applicationContext.getSystemService(NotificationManager::class.java)
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(applicationContext, NammaHasiruApp.REMINDER_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText("Time to update your plant growth status!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(Random.nextInt(), notification)
    }
}

