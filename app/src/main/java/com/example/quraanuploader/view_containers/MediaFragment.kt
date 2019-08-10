package com.example.quraanuploader.view_containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.quraanuploader.R
import com.example.quraanuploader.enities.CreateMedia
import com.example.quraanuploader.ui.ShowSelectionDialog
import com.example.quraanuploader.ui.showEditTextDialog
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
        setOnAddClick()
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
        mainViewModel.liveLoading.observe(this, Observer { setLoading(it) })
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.INVISIBLE
    }

    private fun observeCreateMedia(createMedia: CreateMedia) {
        mainViewModel.createList(createMedia).observe(this, Observer {
            if (it.data.id != null) {
                Toast.makeText(context, "Directory created", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setOnAddClick() {
        add.setOnClickListener {
            showSelectionDialog()
        }
    }

    private fun showSelectionDialog() {
        this.fragmentManager?.run {
            val dialog = ShowSelectionDialog().createNewDialog(R.string.choose_action, arrayListOf(getString(R.string.upload_file),getString(R.string.create_directory))
            )
            dialog.show(this, "selection dialog")
            dialog.setTargetFragment(this@MediaFragment, 1)
        }

    }

    fun doOnItemSelection(itemClicked: String) {
        when (itemClicked) {
            resources.getString(R.string.upload_file)->Toast.makeText(context,"coming soon",Toast.LENGTH_SHORT).show()
            resources.getString(R.string.create_directory)->this.showEditTextDialog({
                if (mediaId!=null){
                    val createMedia=CreateMedia(it,mediaId!!)
                    observeCreateMedia(createMedia)
                }else{
                    val createMedia=CreateMedia(it, MAIN_MEDIA_ID)
                    observeCreateMedia(createMedia)
                }
            },{
                text,button->
                button.isClickable = !text.isBlank()
            })
        }
    }
    companion object{
        private const val MAIN_MEDIA_ID="main-media"
    }
}


