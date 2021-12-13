package com.monica.nagwamediatask.di.modules


import com.monica.nagwamediatask.data.repository.MediaRepositoryImp
import com.monica.nagwamediatask.domain.repository.MediaRepository
import com.monica.nagwamediatask.utils.FileDownloader
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class MediaRepositoryModule {

    @Provides
    fun provideMediaRepository(): MediaRepository {
        return MediaRepositoryImp()
    }

    @Provides
    fun provideFileDownloader(okHttpClient: OkHttpClient) = FileDownloader(okHttpClient)
}