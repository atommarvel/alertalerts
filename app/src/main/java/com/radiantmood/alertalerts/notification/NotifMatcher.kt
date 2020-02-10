package com.radiantmood.alertalerts.notification

import com.radiantmood.alertalerts.data.entity.RuleAttrType
import com.radiantmood.alertalerts.data.entity.RuleAttribute
import com.radiantmood.alertalerts.repo.PrefsRepo
import com.radiantmood.alertalerts.repo.RulesRepo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotifMatcher @Inject constructor(private val rulesRepo: RulesRepo, private val prefsRepo: PrefsRepo) {

    suspend fun doesStringsMatchRule(strings: List<String>) =
        // TODO: always ignore notifs from itself
        prefsRepo.masterSwitch &&
                doesRuleMatch(strings) &&
                enoughTimePassed() &&
                isBeyondSnoozeBarrier()

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

    suspend fun doesRuleMatch(strings: List<String>): Boolean =
        rulesRepo.getEnabledRules().firstOrNull { rule ->
            val attrs = rulesRepo.getAttributes(rule)
            (!attrs.shouldSuppress(strings) && attrs.shouldTrigger(strings))
        } != null

    private fun List<RuleAttribute>.shouldSuppress(strings: List<String>): Boolean =
        isAttrTypeMatching(RuleAttrType.SUPPRESS, strings)

    private fun List<RuleAttribute>.shouldTrigger(strings: List<String>): Boolean =
        isAttrTypeMatching(RuleAttrType.TRIGGER, strings)

    private fun List<RuleAttribute>.isAttrTypeMatching(type: RuleAttrType, strings: List<String>): Boolean {
        return filter { it.type == type }.firstOrNull { attr ->
            strings.firstOrNull {
                attr.target.contains(it, ignoreCase = true)
            } != null
        } != null
    }
}