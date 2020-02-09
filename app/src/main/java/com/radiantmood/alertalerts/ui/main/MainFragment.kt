package com.radiantmood.alertalerts.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.BaseFragment
import com.radiantmood.alertalerts.core.muffleSet
import com.radiantmood.alertalerts.core.prefName
import com.radiantmood.alertalerts.core.triggerSet
import com.radiantmood.alertalerts.di.DaggerMainComponent
import com.radiantmood.alertalerts.di.MainComponent
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : BaseFragment() {

    private val prefs by lazy { context!!.getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var controller: MainEController

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupHardCodedPrefs()
        rules_rv.setController(controller)
        controller.observeForMainModel(this, viewModel.mainModelLiveData)
        viewModel.getData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initMainComponent()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkNotifListenerPermission()
    }

    private fun initMainComponent() {
        mainComponent = DaggerMainComponent.builder()
            .fragmentComponent(fragmentComponent)
            .build()
            .also { it.inject(this) }
    }

    private fun setupHardCodedPrefs() {
        if (prefs.contains(triggerSet)) return
        prefs.edit {
            putStringSet(triggerSet, mutableSetOf("emilia", "poppe", "alertalert", "dove-y"))
            putStringSet(muffleSet, mutableSetOf("silent"))
        }
    }

}
