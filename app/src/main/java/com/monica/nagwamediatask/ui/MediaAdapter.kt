package com.monica.nagwamediatask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.monica.nagwamediatask.R
import com.monica.nagwamediatask.data.models.MediaItem
import com.monica.nagwamediatask.data.models.Status
import kotlinx.android.synthetic.main.item_media.view.*

class MediaAdapter(
    private val mediaList: ArrayList<MediaItem>,
    val onItemClick: (mediaItem: MediaItem, index: Int) -> Unit
) : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {

    override fun getItemCount() = mediaList.size


    fun addAll(newOffers: List<MediaItem>) {
        mediaList.clear()
        mediaList.addAll(newOffers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(mediaList[position])
    }

    inner class MediaViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun bind(mediaItem: MediaItem) {

            when (mediaItem.status) {
                Status.DOWNLOADED -> {
                    rootView.imageDownload.setImageDrawable(
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_right)
                    )
                }
                Status.ERROR -> {
                    rootView.imageDownload.setImageDrawable(
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_error)
                    )
                }
                Status.PENDING -> {
                    rootView.imageDownload.setImageDrawable(
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_pending)
                    )
                }
                Status.INITIAL -> {
                    rootView.imageDownload.setImageDrawable(
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_download)
                    )
                }
            }
            rootView.name.text = mediaItem.name
            rootView.url.text = mediaItem.url

            rootView.progressBar.progress = mediaItem.progress
            rootView.url.setOnClickListener {
                onItemClick(mediaItem, adapterPosition)
            }
        }
    }

    fun showProgressUpdate(progress: Int, index: Int) {
        mediaList[index].progress = progress
        notifyItemChanged(index)
    }

    fun updateItemStatus(index: Int, status: Status) {
        mediaList[index].status = status
        notifyItemChanged(index)
    }


}