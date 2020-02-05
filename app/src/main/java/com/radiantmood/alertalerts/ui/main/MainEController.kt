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
import com.radiantmood.alertalerts.ruleItem
import com.radiantmood.alertalerts.snifferPrompt
import javax.inject.Inject

data class MainModel(val showSnifferPrompt: Boolean, val rules: List<Rule>)

class MainEController @Inject constructor() : TypedEpoxyController<MainModel>(), MainHandlers {

    val snifferId = 1

    @Inject
    lateinit var activity: FragmentActivity

    fun observeForMainModel(lifecycleOwner: LifecycleOwner, liveData: LiveData<MainModel>) {
        liveData.observe(lifecycleOwner, Observer {
            setData(it)
        })
    }

    override fun buildModels(mainModel: MainModel) {
        if (mainModel.showSnifferPrompt) {
            snifferPrompt {
                id(snifferId)
                mainHandlers(this@MainEController)
            }
        }
        mainModel.rules.forEach {
            ruleItem {
                id(it.id)
                name(it.name)
            }
        }
    }

    override fun navToNotifSettings(view: View) {
        activity.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

}