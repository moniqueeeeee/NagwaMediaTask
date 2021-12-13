package com.monica.nagwamediatask.di.components

import android.content.Context
import com.monica.nagwamediatask.MediaApplication
import com.monica.nagwamediatask.di.modules.NetworkModule
import com.monica.nagwamediatask.di.modules.SCHEDULER_IO
import com.monica.nagwamediatask.di.modules.SCHEDULER_MAIN_THREAD
import com.monica.nagwamediatask.di.modules.SchedulerModule
import dagger.BindsInstance
import dagger.Component
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SchedulerModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mediaApplication: MediaApplication)

    @Named(SCHEDULER_MAIN_THREAD)
    fun getMainThread(): Scheduler

    @Named(SCHEDULER_IO)
    fun getIOThread(): Scheduler

    fun getRetrofit(): Retrofit
    fun getOKHttp(): OkHttpClient


}