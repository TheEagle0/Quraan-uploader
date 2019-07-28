package com.example.quraanuploader.view_containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.quraanuploader.R
import kotlinx.android.synthetic.main.fragment_media.*

/**
 * A simple [Fragment] subclass.
 */
class MediaFragment : Fragment() {
    private val mainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MediaViewModel::class.java)
    }
    private val mediaId by lazy { arguments?.getString("id") }
    private val adapter by lazy { MediaAdaptar(mutableListOf(), this) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_media, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpList()
        observeMedia()
        observeLoading()
    }

    private fun setUpList() {
        context?.let {
            rv.layoutManager = LinearLayoutManager(it)
            rv.adapter = adapter
        }
    }

    private fun observeMedia() {
        mainViewModel.getMediaList().observe(this, Observer { media ->
            if (media != null) {
                if (mediaId != null) {
                    val list = media.filter { it.parentId == mediaId }
                    adapter.updateAdapter(list)
                } else {
                    val list = media.filter { it.parentId == "main-media" }
                    adapter.updateAdapter(list)
                }
            }
        })
    }

    private fun observeLoading() {
        mainViewModel.liveLoading.observe(this, Observer {setLoading(it)})
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) progressBar.visibility=View.VISIBLE
        else progressBar.visibility=View.INVISIBLE
    }
}
