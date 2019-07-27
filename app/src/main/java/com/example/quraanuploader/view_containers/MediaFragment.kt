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
private val adaptar by lazy { MediaAdaptar(mutableListOf()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpList()
        observeMedia()
    }

    private fun setUpList() {
        context?.let {
            rv.layoutManager=LinearLayoutManager(it)
            rv.adapter=adaptar
        }
    }

    private fun observeMedia() {
        mainViewModel.getMediaList().observe(this, Observer {
            adaptar.updateAdapter(it)
        })
    }
}
