package com.radiantmood.alertalerts.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Typed2EpoxyController
import com.radiantmood.alertalerts.*
import com.radiantmood.alertalerts.core.App
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.di.DaggerFragmentComponent
import com.radiantmood.alertalerts.di.FragmentComponent
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : Fragment() {

    private val prefs by lazy { context!!.getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
    }

    val controller = MainController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentComponent = DaggerFragmentComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .also { it.inject(this) }

        sniff_btn.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        setupHardCodedPrefs()


        rules_rv.layoutManager = LinearLayoutManager(context)
        rules_rv.adapter = controller.adapter


        viewModel.rulesLiveData.observe(this, Observer { rules ->
            Log.d("raiff", rules[0].name)
            controller.setData(true, rules)
//            controller.requestModelBuild()
        })

        viewModel.getRules()
    }

    private fun setupHardCodedPrefs() {
        if (prefs.contains(triggerSet)) return
        prefs.edit {
            putStringSet(triggerSet, mutableSetOf("emilia", "poppe", "alertalert", "dove-y"))
            putStringSet(muffleSet, mutableSetOf("silent"))
        }
    }

}

//TODO: switch to epoxy recyclerview
class MainController : Typed2EpoxyController<Boolean, List<Rule>>() {
    override fun buildModels(showSnifferBanner: Boolean, rules: List<Rule>) {
        rules.forEach {
            ruleItem {
                id(it.id)
                name(it.name)
            }
        }
    }

}
