package com.radiantmood.alertalerts.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.BaseFragment
import com.radiantmood.alertalerts.di.DaggerMainComponent
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : BaseFragment() {

    @Inject
    lateinit var controller: MainEController

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        DaggerMainComponent.builder()
            .fragmentComponent(fragmentComponent)
            .build()
            .also { it.inject(this) }
    }
}
