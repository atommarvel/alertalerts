package com.radiantmood.alertalerts.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.*
import java.util.*
import java.util.concurrent.TimeUnit

class NotifListener : NotificationListenerService() {

    private val prefs by lazy { getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    override fun onNotificationPosted(sbn: StatusBarNotification, rankingMap: RankingMap) {
        super.onNotificationPosted(sbn, rankingMap)
        if (!shouldAlertMuffle(sbn)) {
            if (shouldAlertAlert(sbn) && enoughTimePassed() && isBeyondSnoozeBarrier()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createChannelIfNeeded()
                }
                postPiercingNotif()
            }
        }
    }

    private fun enoughTimePassed(): Boolean {
        val now = Calendar.getInstance().timeInMillis
        val latestPierce = prefs.getLong(lastPierce, 0)
        val timeHasPassed = now - latestPierce > TimeUnit.SECONDS.toMillis(5)
        if (timeHasPassed) {
            prefs.edit { putLong(lastPierce, now) }
        }
        return timeHasPassed
    }

    private fun isBeyondSnoozeBarrier(): Boolean {
        val now = Calendar.getInstance().timeInMillis
        val snoozeBarrier = prefs.getLong(snoozeBarrier, 0)
        return now > snoozeBarrier
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelIfNeeded() {
        val channel = NotificationChannel(channelId, "Alert!Alert!", IMPORTANCE_DEFAULT).apply {
            description = "Get sound alerts for specific silent alerts"
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun postPiercingNotif() {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alert!Alert!")
            .setContentText("A notification has triggered!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(Notification.CATEGORY_ALARM)
            .addAction(getSnoozeNotifAction())
        with(NotificationManagerCompat.from(this)) {
            notify(notifId, builder.build())
        }
    }

    private fun getSnoozeNotifAction(): NotificationCompat.Action {
        val intent = Intent(this, NotifActionReceiver::class.java).apply {
            putActionType(NotifActionType.SNOOZE)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            snoozeReqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action(R.drawable.ic_launcher_foreground, "Snooze 5 min", pendingIntent)
    }

    private fun shouldAlertMuffle(sbn: StatusBarNotification): Boolean =
        doesSavedSetContainStrings(muffleSet, *sbn.getNotificationStrings())

    private fun shouldAlertAlert(sbn: StatusBarNotification): Boolean =
        doesSavedSetContainStrings(triggerSet, *sbn.getNotificationStrings())

    private fun doesSavedSetContainStrings(setKey: String, vararg stringsToCheck: String): Boolean {
        val savedSet = requireNotNull(prefs.getStringSet(setKey, mutableSetOf()))
        return savedSet.containsSubstrings(*stringsToCheck)
    }

    private fun MutableSet<String>.containsSubstrings(vararg stringsToSearchFor: String) =
        null != firstOrNull { stringInSet ->
            null != stringsToSearchFor.firstOrNull { it.contains(stringInSet, true) }
        }

    private fun StatusBarNotification.getNotificationStrings() = arrayOf(title, text)

    private val StatusBarNotification.title
        get() = this.notification.extras.getCharSequence(Notification.EXTRA_TEXT, "").toString()
            .toLowerCase(Locale.ROOT)

    private val StatusBarNotification.text
        get() = this.notification.extras.getCharSequence(Notification.EXTRA_TITLE, "")
            .toString().toLowerCase(Locale.ROOT)
}