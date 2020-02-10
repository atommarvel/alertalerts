package com.radiantmood.alertalerts.di

import com.radiantmood.alertalerts.notification.NotifListener
import dagger.Component
import javax.inject.Scope

@Scope
annotation class PerNotifListener

@PerNotifListener
@Component(
    dependencies = [AppComponent::class]
)
interface NotifListenerComponent {
    fun inject(listener: NotifListener)
}