package com.monica.nagwamediatask.di.modules

import com.monica.nagwamediatask.domain.repository.MediaRepository
import com.monica.nagwamediatask.domain.usecases.MediaUseCases
import com.monica.nagwamediatask.domain.usecases.MediaUseCasesImp
import dagger.Module
import dagger.Provides

@Module
class MediaUseCasesModule {

    @Provides
    fun provideMediaUseCases(
        mediaRepository: MediaRepository
    ): MediaUseCases {
        return MediaUseCasesImp(mediaRepository)
    }

}