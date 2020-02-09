package com.radiantmood.alertalerts.repo

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import com.radiantmood.alertalerts.notification.NotifListener
import javax.inject.Inject

/**
 * Check if [NotifListener] is enabled by Android system.
 */
class NotifListenerPermissionRepo @Inject constructor(private val context: Context) {
    fun isNotifListenerPermissionEnabled(): Boolean {
        val cn = ComponentName(context, NotifListener::class.java)
        val flat: String? =
            Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return flat?.contains(cn.flattenToString()) == true
    }
}