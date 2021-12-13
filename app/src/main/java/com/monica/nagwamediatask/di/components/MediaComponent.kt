package com.monica.nagwamediatask.di.components

import com.monica.nagwamediatask.di.modules.MediaRepositoryModule
import com.monica.nagwamediatask.di.modules.MediaUseCasesModule
import com.monica.nagwamediatask.di.modules.MediaViewModelModule
import com.monica.nagwamediatask.di.scopes.MediaScope
import com.monica.nagwamediatask.ui.MediaFragment
import dagger.Component


@MediaScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MediaRepositoryModule::class,
        MediaUseCasesModule::class,
        MediaViewModelModule::class,
    ]
)
interface MediaComponent {

    @Component.Factory
    interface MediaComponentFactory {
        fun create(appComponent: AppComponent): MediaComponent
    }

    fun inject(mediaFragment: MediaFragment)


}