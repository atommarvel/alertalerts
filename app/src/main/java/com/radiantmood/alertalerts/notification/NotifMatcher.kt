package com.radiantmood.alertalerts.notification

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.radiantmood.alertalerts.BuildConfig
import com.radiantmood.alertalerts.data.entity.RuleAttrType
import com.radiantmood.alertalerts.data.entity.RuleAttribute
import com.radiantmood.alertalerts.repo.PrefsRepo
import com.radiantmood.alertalerts.repo.RulesRepo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotifMatcher @Inject constructor(private val rulesRepo: RulesRepo, private val prefsRepo: PrefsRepo) {

    suspend fun doesNotificationMatchRule(sbn: StatusBarNotification): Boolean {
        val strings = sbn.getNotificationStrings()
        return !sbn.isFromAlertAlerts() &&
                prefsRepo.masterSwitch &&
                doesRuleMatch(strings, sbn.packageName) &&
                enoughTimePassed() &&
                isBeyondSnoozeBarrier()
    }

    private fun enoughTimePassed(): Boolean {
        val now = Calendar.getInstance().timeInMillis
        val latestPierce = prefsRepo.lastPierce
        // TODO: 5 second check customizable by the user
        val timeHasPassed = now - latestPierce > TimeUnit.SECONDS.toMillis(5)
        if (timeHasPassed) {
            prefsRepo.lastPierce = now
        }
        return timeHasPassed
    }

    private fun isBeyondSnoozeBarrier(): Boolean {
        val now = Calendar.getInstance().timeInMillis
        return now > prefsRepo.snoozeBarrier
    }

    private suspend fun doesRuleMatch(strings: List<String>, packageName: String): Boolean =
        rulesRepo.getEnabledRules().firstOrNull { rule ->
            val attrs = rulesRepo.getAttributes(rule)
            (attrs.meetsAppFilter(packageName) && !attrs.shouldSuppress(strings) && attrs.shouldTrigger(strings))
        } != null

    private fun List<RuleAttribute>.shouldSuppress(strings: List<String>): Boolean =
        isAttrTypeMatching(RuleAttrType.SUPPRESS, strings)

    private fun List<RuleAttribute>.shouldTrigger(strings: List<String>): Boolean =
        isAttrTypeMatching(RuleAttrType.TRIGGER, strings)

    private fun List<RuleAttribute>.meetsAppFilter(packageName: String): Boolean {
        val appFilters: List<RuleAttribute> = filter { it.type == RuleAttrType.APP_FILTER }
        return if (appFilters.isNotEmpty()) {
            appFilters.firstOrNull {
                it.target == packageName
            } != null
        } else {
            true
        }
    }

    private fun List<RuleAttribute>.isAttrTypeMatching(type: RuleAttrType, strings: List<String>): Boolean =
        filter { it.type == type }.firstOrNull { attr ->
            strings.firstOrNull {
                attr.target.contains(it, ignoreCase = true)
            } != null
        } != null

    private fun StatusBarNotification.isFromAlertAlerts() = packageName == BuildConfig.APPLICATION_ID

    private fun StatusBarNotification.getNotificationStrings() = listOf(title, text)

    private val StatusBarNotification.title
        get() = this.notification.extras.getCharSequence(Notification.EXTRA_TEXT, "").toString()
            .toLowerCase(Locale.ROOT)

    private val StatusBarNotification.text
        get() = this.notification.extras.getCharSequence(Notification.EXTRA_TITLE, "")
            .toString().toLowerCase(Locale.ROOT)
}