package com.radiantmood.alertalerts.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.channelId
import com.radiantmood.alertalerts.core.notifId
import com.radiantmood.alertalerts.core.snoozeReqCode
import com.radiantmood.alertalerts.repo.PrefsRepo
import javax.inject.Inject

class NotifPoster @Inject constructor(private val context: Context, private val prefsRepo: PrefsRepo) {
    fun postPiercingNotif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelIfNeeded()
        }
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alert!Alert!")
            .setContentText("A notification has triggered!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(Notification.CATEGORY_ALARM)
            .addAction(getSnoozeNotifAction())
        with(NotificationManagerCompat.from(context)) {
            notify(notifId, builder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelIfNeeded() {
        val channel = NotificationChannel(channelId, "Alert!Alert!", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Get sound alerts for specific silent alerts"
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun getSnoozeNotifAction(): NotificationCompat.Action {
        val intent = Intent(context, NotifActionReceiver::class.java).apply {
            putActionType(NotifActionType.SNOOZE)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            snoozeReqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action(
            R.drawable.ic_launcher_foreground,
            "Snooze ${prefsRepo.snoozeDuration} min",
            pendingIntent
        )
    }
}