package com.monica.nagwamediatask.domain.repository

import android.content.Context
import com.monica.nagwamediatask.data.models.MediaItem

interface MediaRepository {

    fun getMediaListFromJSOnFile(context: Context):ArrayList<MediaItem>
}