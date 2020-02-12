package com.radiantmood.alertalerts.ui.main

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.airbnb.epoxy.TypedEpoxyController
import com.radiantmood.alertalerts.core.ruleHeaderId
import com.radiantmood.alertalerts.core.snifferId
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.ui.common.NavToNotifSettingsHandler
import com.radiantmood.alertalerts.ui.common.renderHeader
import com.radiantmood.alertalerts.ui.common.renderRules
import com.radiantmood.alertalerts.ui.common.renderSnifferPrompt
import javax.inject.Inject

/**
 * Container for all the data to render on the main screen.
 */
data class MainModel(val showSnifferPrompt: Boolean, val title: String, val rules: List<Rule>)

class MainEController @Inject constructor() : TypedEpoxyController<MainModel>(), NavToNotifSettingsHandler {

    @Inject
    lateinit var fragment: Fragment

    fun observeForMainModel(lifecycleOwner: LifecycleOwner, liveData: LiveData<MainModel>) {
        liveData.observe(lifecycleOwner, Observer {
            setData(it)
        })
    }

    override fun buildModels(mainModel: MainModel) = with(mainModel) {
        renderSnifferPrompt(showSnifferPrompt, this@MainEController, snifferId)
        renderHeader(title, ruleHeaderId)
        renderRules(rules)
    }

    override fun navToNotifSettings(view: View) {
        fragment.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

}