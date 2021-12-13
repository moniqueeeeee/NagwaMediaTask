package com.monica.nagwamediatask.ui

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import com.monica.nagwamediatask.base.BaseViewModel
import com.monica.nagwamediatask.data.models.MediaItem
import com.monica.nagwamediatask.di.modules.SCHEDULER_IO
import com.monica.nagwamediatask.di.modules.SCHEDULER_MAIN_THREAD
import com.monica.nagwamediatask.domain.usecases.MediaUseCases
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named


class MediaViewModel @Inject
constructor(
    private val mediaUseCases: MediaUseCases,
    @Named(SCHEDULER_IO) private val schedulerIo: Scheduler,
    @Named(SCHEDULER_MAIN_THREAD) private val schedulerMain: Scheduler
) : BaseViewModel(schedulerIo, schedulerMain) {


    private lateinit var downloadManager: DownloadManager
    private lateinit var request: DownloadManager.Request
    private var url: String? = null
    private var query: DownloadManager.Query? = null
    private lateinit var cursor: Cursor
    private var downloadId: Long = -1
    private var index = -1
    private var mapDownloadItem: Map<Long, Int>? = null


    fun getMediaList(context: Context) {
        subscribe(
            request = mediaUseCases.getMediaListFromJSOnFile(context),
            success = {
                internalState.value = MediaViewState.GetMediaList(it)
            })
    }

    val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == id) {
                val adapterIndex = mapDownloadItem?.get(downloadId)
                internalState.value = MediaViewState.DownloadSuccess(adapterIndex!!)
            }
        }
    }

    fun downloadMedia(context: Context, mediaItem: MediaItem, index: Int) {

        this.index = index
        createDownloadManagerRequest(context, mediaItem)
        subscribeFlowable(getDownloadStatus().toFlowable(BackpressureStrategy.BUFFER),
            success = {
                when (it) {
                    DownloadManager.STATUS_FAILED ->
                        internalState.value = MediaViewState.ErrorDownload(index)

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        internalState.value = MediaViewState.SuccessDownload
                        internalState.value = MediaViewState.ProgressDownload(100, index)

                    }

                    DownloadManager.STATUS_PENDING -> {
                        internalState.value = MediaViewState.DownloadPending(index)
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        val bytesToDownload = cursor.getInt(
                            cursor
                                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                        )
                        val totalBytes =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                        val progressValue = (bytesToDownload / totalBytes * 100).toDouble()
                        internalState.value =
                            MediaViewState.ProgressDownload(progressValue.toInt(), index)
                    }

                }
            }, onNext = {
                internalState.value = MediaViewState.ProgressDownload(it, index)

            }, error = {
                internalState.value = MediaViewState.ErrorDownload(index)

            }
        )
    }

    private fun createDownloadManagerRequest(context: Context?, mediaItem: MediaItem) {
        downloadManager =
            context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        url = mediaItem.url
        val downloadUri = Uri.parse(url)

        request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setTitle(url?.substring(url!!.lastIndexOf("/") + 1))
                .setDescription("Download")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    url?.substring(url!!.lastIndexOf("/") + 1)
                )
        }
        downloadId = downloadManager.enqueue(request)
        mapDownloadItem = mapOf(downloadId to index)
        query = DownloadManager.Query().setFilterById(downloadId)
    }

    private fun getDownloadStatus(): Observable<Int> {
        val c = downloadManager.query(query)
        if (c.moveToFirst()) {
            val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
            c.close()
            return Observable.just(status)
        }
        return Observable.empty()
    }
}