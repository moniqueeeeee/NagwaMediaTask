package com.monica.nagwamediatask.ui

import com.monica.nagwamediatask.base.BaseViewState
import com.monica.nagwamediatask.data.models.MediaItem

sealed class MediaViewState : BaseViewState() {
    class GetMediaList(val mediaList: ArrayList<MediaItem>?) : MediaViewState()

    object SuccessDownload : MediaViewState()

    data class ErrorDownload(val index: Int) : MediaViewState()

    data class ProgressDownload(val progress: Int, val index: Int) : MediaViewState()
    class DownloadSuccess(val index: Int) : MediaViewState()
    class DownloadPending(val index: Int) : MediaViewState()


}