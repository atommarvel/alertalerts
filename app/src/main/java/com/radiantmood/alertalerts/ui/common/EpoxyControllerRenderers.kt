package com.radiantmood.alertalerts.ui.common

import com.airbnb.epoxy.EpoxyController
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.ruleItem
import com.radiantmood.alertalerts.snifferPrompt

private const val snifferId = 999

fun EpoxyController.renderSnifferPrompt(showSnifferPrompt: Boolean, handler: NavToNotifSettingsHandler) {
    if (showSnifferPrompt) {
        snifferPrompt {
            id(snifferId)
            handler(handler)
        }
    }
}

fun EpoxyController.renderRules(rules: List<Rule>) {
    rules.forEach {
        ruleItem {
            id(it.id)
            name(it.name)
        }
    }
}