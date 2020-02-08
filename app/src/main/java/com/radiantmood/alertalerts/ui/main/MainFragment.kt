package com.radiantmood.alertalerts.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.App
import com.radiantmood.alertalerts.di.DaggerFragmentComponent
import com.radiantmood.alertalerts.di.FragmentActivityModule
import com.radiantmood.alertalerts.di.FragmentComponent
import com.radiantmood.alertalerts.muffleSet
import com.radiantmood.alertalerts.prefName
import com.radiantmood.alertalerts.triggerSet
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : Fragment() {

    private val prefs by lazy { context!!.getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    lateinit var fragmentComponent: FragmentComponent

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
        fragmentComponent = DaggerFragmentComponent.builder()
            .appComponent(App.appComponent)
            .fragmentActivityModule(FragmentActivityModule(activity!!))
            .build()
            .also { it.inject(this) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkNotifListenerPermission()
    }

    private fun setupHardCodedPrefs() {
        if (prefs.contains(triggerSet)) return
        prefs.edit {
            putStringSet(triggerSet, mutableSetOf("emilia", "poppe", "alertalert", "dove-y"))
            putStringSet(muffleSet, mutableSetOf("silent"))
        }
    }

}
