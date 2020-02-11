package com.radiantmood.alertalerts.ui.common

import com.airbnb.epoxy.EpoxyController
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.header
import com.radiantmood.alertalerts.ruleItem
import com.radiantmood.alertalerts.snifferPrompt

fun EpoxyController.renderSnifferPrompt(showSnifferPrompt: Boolean, handler: NavToNotifSettingsHandler, id: Int) {
    if (showSnifferPrompt) {
        snifferPrompt {
            id(id)
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

fun EpoxyController.renderHeader(title: String, id: Int) {
    header {
        id(id)
        title(title)
    }
}