package com.monica.nagwamediatask

import android.app.Application
import com.monica.nagwamediatask.di.components.AppComponent
import com.monica.nagwamediatask.di.components.DaggerAppComponent

class MediaApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this.applicationContext)
        appComponent.inject(this)
    }
}