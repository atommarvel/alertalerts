package com.radiantmood.alertalerts.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import com.radiantmood.alertalerts.core.notifId
import com.radiantmood.alertalerts.core.prefName
import com.radiantmood.alertalerts.core.snoozeBarrierPref
import com.radiantmood.alertalerts.repo.PrefsRepo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotifActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prefsRepo: PrefsRepo

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getActionType()) {
            NotifActionType.SNOOZE -> snoozeNotifs(context, intent)
        }
    }

    private fun snoozeNotifs(context: Context, intent: Intent) {
        val snoozeDurationMillis = TimeUnit.MINUTES.toMillis(prefsRepo.snoozeDuration.toLong())
        val fiveAhead = Calendar.getInstance().timeInMillis + snoozeDurationMillis
        getPrefs(context).edit {
            putLong(snoozeBarrierPref, fiveAhead)
        }
        with(NotificationManagerCompat.from(context)) {
            cancel(notifId)
        }
        Toast.makeText(context, "Snoozing alerts...", Toast.LENGTH_SHORT).show()
    }


    private fun getPrefs(context: Context) = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
}