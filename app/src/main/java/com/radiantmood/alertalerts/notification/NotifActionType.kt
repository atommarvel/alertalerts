package com.radiantmood.alertalerts.notification

import android.content.Intent

enum class NotifActionType {
    SNOOZE
}

private const val KEY_ACTION = "alertalerts.actionType"
fun Intent.putActionType(type: NotifActionType): Intent = putExtra(KEY_ACTION, type)
fun Intent.getActionType(): NotifActionType = getSerializableExtra(KEY_ACTION) as NotifActionType