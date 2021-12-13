package com.monica.nagwamediatask.domain.usecases

import android.content.Context
import com.monica.nagwamediatask.data.models.MediaItem
import io.reactivex.Observable

interface MediaUseCases {
    fun getMediaListFromJSOnFile(context: Context): Observable<ArrayList<MediaItem>>
}