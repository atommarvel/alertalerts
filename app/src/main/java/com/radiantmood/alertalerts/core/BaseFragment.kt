package com.radiantmood.alertalerts.core

import android.content.Context
import androidx.fragment.app.Fragment
import com.radiantmood.alertalerts.di.DaggerFragmentComponent
import com.radiantmood.alertalerts.di.FragmentActivityModule
import com.radiantmood.alertalerts.di.FragmentComponent

open class BaseFragment : Fragment() {
    lateinit var fragmentComponent: FragmentComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent = DaggerFragmentComponent.builder()
            .appComponent(App.appComponent)
            .fragmentActivityModule(FragmentActivityModule(activity!!))
            .build()
    }
}