package com.radiantmood.alertalerts

import android.content.Intent

enum class ActionType {
    SNOOZE
}

private const val KEY_ACTION = "alertalerts.actionType"
fun Intent.putActionType(type: ActionType): Intent = putExtra(KEY_ACTION, type)
fun Intent.getActionType(): ActionType = getSerializableExtra(KEY_ACTION) as ActionType