package com.example.quraanuploader.view_containers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quraanuploader.R
import com.example.quraanuploader.enities.Media
import kotlinx.android.synthetic.main.media_item.view.*

class MediaAdaptar(private val mediaList: MutableList<Media.Data>) :
    RecyclerView.Adapter<MediaAdaptar.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.media_item, parent, false)
        )
    }

    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = mediaList[position]
        with(holder.itemView) {
            this.itemTitle.text = media.title
        }
    }

    fun updateAdapter(newMediaList: List<Media.Data>) {
        this.mediaList.clear()
        this.mediaList.addAll(newMediaList)
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}