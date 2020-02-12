package com.radiantmood.alertalerts.ui.ruleform

import com.airbnb.epoxy.TypedEpoxyController

data class RuleFormModel(val holder: Boolean = true)

class RuleFormEController : TypedEpoxyController<RuleFormViewModel>() {
    override fun buildModels(data: RuleFormViewModel) {
        // TODO: RESUME - render formTop and add listeners
        // title edit text
        // rule enabled/disabled
        // add app filter
        // current app filters
        // trigger edit text & add
        // current triggers with deletion
        // suppress edit text & add
        // current suppress with deletion
    }
}