package com.radiantmood.alertalerts.ui.main

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.airbnb.epoxy.TypedEpoxyController
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.ui.common.NavToNotifSettingsHandler
import com.radiantmood.alertalerts.ui.common.renderRules
import com.radiantmood.alertalerts.ui.common.renderSnifferPrompt
import javax.inject.Inject

/**
 * Container for all the data to render on the main screen.
 */
data class MainModel(val showSnifferPrompt: Boolean, val rules: List<Rule>)

class MainEController @Inject constructor() : TypedEpoxyController<MainModel>(), NavToNotifSettingsHandler {

    @Inject
    lateinit var activity: FragmentActivity

    fun observeForMainModel(lifecycleOwner: LifecycleOwner, liveData: LiveData<MainModel>) {
        liveData.observe(lifecycleOwner, Observer {
            setData(it)
        })
    }

    override fun buildModels(mainModel: MainModel) {
        renderSnifferPrompt(mainModel.showSnifferPrompt, this)
        renderRules(mainModel.rules)
    }

    override fun navToNotifSettings(view: View) {
        activity.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

}