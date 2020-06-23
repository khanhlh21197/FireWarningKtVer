package com.khanhlh.firewarningkt.di

import com.khanhlh.firewarningkt.MyApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class, DbModule::class])
interface AppComponent {
    fun inject(app: MyApp)
}