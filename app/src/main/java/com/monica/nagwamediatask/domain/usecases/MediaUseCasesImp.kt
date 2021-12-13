package com.monica.nagwamediatask.domain.usecases

import android.content.Context
import com.monica.nagwamediatask.data.models.MediaItem
import com.monica.nagwamediatask.domain.repository.MediaRepository
import io.reactivex.Observable
import javax.inject.Inject

class MediaUseCasesImp @Inject constructor(var mediaRepository: MediaRepository) : MediaUseCases {
    override fun getMediaListFromJSOnFile(context: Context): Observable<ArrayList<MediaItem>> {
        return Observable.just(mediaRepository.getMediaListFromJSOnFile(context))
    }
}