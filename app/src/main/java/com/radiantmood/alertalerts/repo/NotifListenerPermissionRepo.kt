package com.radiantmood.alertalerts.repo

import android.content.ComponentName
import android.provider.Settings
import androidx.fragment.app.FragmentActivity
import com.radiantmood.alertalerts.AlertSniffer
import javax.inject.Inject


class NotifListenerPermissionRepo @Inject constructor(private val fragmentActivity: FragmentActivity) {
    fun isNotifListenerPermissionEnabled(): Boolean {
        val context = fragmentActivity
        val cn = ComponentName(context, AlertSniffer::class.java)
        val flat: String? =
            Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return flat?.contains(cn.flattenToString()) == true
    }
}