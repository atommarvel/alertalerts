package com.radiantmood.alertalerts.core

import android.content.Context
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.radiantmood.alertalerts.di.DaggerFragmentComponent
import com.radiantmood.alertalerts.di.FragmentComponent
import com.radiantmood.alertalerts.di.FragmentModule
import javax.inject.Inject

open class BaseFragment : Fragment() {

    lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onDI()
    }

    @CallSuper
    open fun onDI() {
        fragmentComponent = DaggerFragmentComponent.builder()
            .appComponent(App.appComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .also { it.inject(this) }
    }

    fun <T : ViewModel> getViewModel(clazz: Class<T>): T =
        ViewModelProvider(this, vmFactory)[clazz]
}