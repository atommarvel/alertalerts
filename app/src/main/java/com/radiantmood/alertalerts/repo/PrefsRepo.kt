package com.radiantmood.alertalerts.repo

import android.content.SharedPreferences
import com.radiantmood.alertalerts.core.*
import com.radiantmood.alertalerts.util.BooleanPrefDelegate
import com.radiantmood.alertalerts.util.IntPrefDelegate
import com.radiantmood.alertalerts.util.LongPrefDelegate
import javax.inject.Inject

class PrefsRepo @Inject constructor(private val prefs: SharedPreferences) {

    var masterSwitch by BooleanPrefDelegate(masterRuleSwitchPref, true, prefs)

    var autoDismiss by BooleanPrefDelegate(autoRemoveNotifPref, false, prefs)

    // TODO: last pierce will be tracked by trigger history in the future
    var lastPierce by LongPrefDelegate(lastPiercePref, 0, prefs)

    var snoozeBarrier by LongPrefDelegate(snoozeBarrierPref, 0, prefs)

    var snoozeDuration by IntPrefDelegate(snoozeDurationPref, 5, prefs)
}