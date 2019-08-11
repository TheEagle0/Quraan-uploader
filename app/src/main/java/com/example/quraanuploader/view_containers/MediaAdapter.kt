package com.example.quraanuploader.view_containers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quraanuploader.R
import com.example.quraanuploader.enities.DeleteMedia
import com.example.quraanuploader.enities.Media
import com.example.quraanuploader.ui.DeletionDialog
import kotlinx.android.synthetic.main.media_item.view.*

class MediaAdapter(
    private val mediaList: MutableList<Media.Data>,
    private val fragment: MediaFragment
) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.media_item, parent, false)
        )
    }

    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media: Media.Data = mediaList[position]
        with(holder.itemView) {
            this.itemTitle.text = media.title
            if (media.isList) {
                itemImageView.setImageResource(R.drawable.ic_playlist)
                itemImageView.background = resources.getDrawable(R.drawable.circle, null)
                this.setOnClickListener {
                    findNavController(fragment).navigate(
                        R.id.action_global_mediaFragment,
                        Bundle().apply {
                            putString("id", media.id)
                        })
                }
            } else {
                itemImageView.setImageResource(R.drawable.ic_audiotrack)
                itemImageView.background = resources.getDrawable(R.drawable.circle, null)
            }
            this.setOnLongClickListener {
                deleteMediaForId(media)
                true
            }
        }

    }

    private fun deleteMediaForId(media: Media.Data) {
        val deleteMedia = DeleteMedia(media.id)
        DeletionDialog {fragment.deleteMedia(deleteMedia)
        }.show(fragment.childFragmentManager,"")
    }

    fun updateAdapter(newMediaList: List<Media.Data>) {
        this.mediaList.clear()
        this.mediaList.addAll(newMediaList)
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
