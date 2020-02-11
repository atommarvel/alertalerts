package com.radiantmood.alertalerts.notification

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.radiantmood.alertalerts.core.App
import com.radiantmood.alertalerts.di.DaggerNotifListenerComponent
import com.radiantmood.alertalerts.di.NotifListenerComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class NotifListener : NotificationListenerService() {

    @Inject
    lateinit var notifMatcher: NotifMatcher

    @Inject
    lateinit var notifPoster: NotifPoster

    lateinit var notifListenerComponent: NotifListenerComponent

    private fun ensureInjection() {
        if (!this::notifListenerComponent.isInitialized) {
            notifListenerComponent = DaggerNotifListenerComponent.builder()
                .appComponent(App.appComponent)
                .build()
                .also { it.inject(this) }
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification, rankingMap: RankingMap) {
        ensureInjection()
        super.onNotificationPosted(sbn, rankingMap)
        /*
           TODO: might need to be workmanager instead since this will get triggered when app is in bg?
           Keep an eye on it.
         */
        GlobalScope.launch(Dispatchers.IO) {
            if (notifMatcher.doesStringsMatchRule(sbn.getNotificationStrings())) {
                notifPoster.postPiercingNotif()
            }
        }
    }

    private fun StatusBarNotification.getNotificationStrings() = listOf(title, text)

    private val StatusBarNotification.title
        get() = this.notification.extras.getCharSequence(Notification.EXTRA_TEXT, "").toString()
            .toLowerCase(Locale.ROOT)

    private val StatusBarNotification.text
        get() = this.notification.extras.getCharSequence(Notification.EXTRA_TITLE, "")
            .toString().toLowerCase(Locale.ROOT)
}