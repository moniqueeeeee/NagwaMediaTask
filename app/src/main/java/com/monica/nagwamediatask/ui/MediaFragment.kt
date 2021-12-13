package com.monica.nagwamediatask.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import com.monica.nagwamediatask.MediaApplication
import com.monica.nagwamediatask.R
import com.monica.nagwamediatask.base.BaseFragment
import com.monica.nagwamediatask.base.BaseViewState
import com.monica.nagwamediatask.data.models.MediaItem
import com.monica.nagwamediatask.data.models.Status
import com.monica.nagwamediatask.di.components.DaggerMediaComponent
import com.monica.nagwamediatask.di.components.MediaComponent
import kotlinx.android.synthetic.main.fragment_media.*


class MediaFragment : BaseFragment<MediaViewModel>(MediaViewModel::class.java) {

    private lateinit var mediaComponent: MediaComponent
    lateinit var mediaAdapter: MediaAdapter

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun askPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    override fun injectDagger() {

        val appComponent = (activity?.applicationContext as MediaApplication).appComponent
        mediaComponent = DaggerMediaComponent.factory().create(appComponent)
        mediaComponent.inject(this)
    }

    override fun getLayout() = R.layout.fragment_media

    override fun initView() {

        mediaAdapter = MediaAdapter(ArrayList(), this::onUrlClicked)
        rvMediaList.adapter = mediaAdapter
    }


    private fun onUrlClicked(mediaItem: MediaItem, index: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            askPermissions()
        } else {
            viewModel.downloadMedia(requireContext(), mediaItem, index)
        }
    }

    override fun renderView(viewState: BaseViewState?) {
        when (viewState) {
            is MediaViewState.GetMediaList -> {
                mediaAdapter.addAll(viewState.mediaList!!)
            }
            is MediaViewState.ProgressDownload -> {
                mediaAdapter.showProgressUpdate(viewState.progress, viewState.index)


            }
            is MediaViewState.SuccessDownload -> {
                requireContext().registerReceiver(
                    viewModel.onDownloadComplete,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                )
            }
            is MediaViewState.ErrorDownload -> {
                mediaAdapter.updateItemStatus(viewState.index, Status.ERROR)

            }
            is MediaViewState.DownloadSuccess -> {
                mediaAdapter.updateItemStatus(viewState.index, Status.DOWNLOADED)
            }
            is MediaViewState.DownloadPending -> {
                mediaAdapter.updateItemStatus(viewState.index, Status.PENDING)
            }
        }
    }


    override fun startRequest() {
        viewModel.getMediaList(requireContext())
    }

    override fun setListeners() {
    }


}