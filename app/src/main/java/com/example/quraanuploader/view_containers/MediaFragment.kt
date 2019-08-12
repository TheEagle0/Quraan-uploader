package com.example.quraanuploader.view_containers

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quraanuploader.R
import com.example.quraanuploader.enities.CreateMedia
import com.example.quraanuploader.enities.DeleteMedia
import com.example.quraanuploader.ui.ShowSelectionDialog
import com.example.quraanuploader.ui.showEditTextDialog
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_media.*
import com.vincent.filepicker.Constant.REQUEST_CODE_PICK_AUDIO
import com.vincent.filepicker.Constant.MAX_NUMBER
import com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER
import com.vincent.filepicker.activity.AudioPickActivity
import android.content.Intent
import android.util.Log
import com.vincent.filepicker.Constant
import com.vincent.filepicker.filter.entity.AudioFile
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MediaFragment : Fragment() {
    private val mainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MediaViewModel::class.java)
    }
    private val mediaId by lazy { arguments?.getString("id") }
    private val adapter by lazy { MediaAdapter(mutableListOf(), this) }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_PICK_AUDIO -> {
                if (resultCode == RESULT_OK) {
                    val list: ArrayList<AudioFile>? =
                        data?.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO)
                    list?.forEach {
                        it.name
                        Log.d(
                            "name", it.name
                        )
                    }
                    Log.d("files", list.toString())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickFiles()
            }
        }
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
                Toasty.success(context!!, "Directory created", Toast.LENGTH_SHORT).show()
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
            val dialog = ShowSelectionDialog().createNewDialog(
                R.string.choose_action,
                arrayListOf(getString(R.string.upload_file), getString(R.string.create_directory))
            )
            dialog.show(this, "selection dialog")
            dialog.setTargetFragment(this@MediaFragment, 1)
        }

    }

    fun doOnItemSelection(itemClicked: String) {
        when (itemClicked) {
            resources.getString(R.string.upload_file) -> pickFiles()

            resources.getString(R.string.create_directory) -> showDialog()
        }
    }

    private fun showDialog() {
        this.showEditTextDialog({
            if (mediaId != null) {
                val createMedia = CreateMedia(it, mediaId!!)
                observeCreateMedia(createMedia)
            } else {
                val createMedia = CreateMedia(it, MAIN_MEDIA_ID)
                observeCreateMedia(createMedia)
            }
        }, { text, button ->
            button.isClickable = !text.isBlank()
        })
    }

    private fun pickFiles() {
        if (onHasStoragePermission()) {
            val intent = Intent(context, AudioPickActivity::class.java)
            intent.putExtra(IS_NEED_RECORDER, true)
            intent.putExtra(MAX_NUMBER, 200)
            startActivityForResult(intent, REQUEST_CODE_PICK_AUDIO)
        } else acquireStoragePermission()
    }

    fun deleteMedia(deleteMedia: DeleteMedia) {
        mainViewModel.deleteMedia(deleteMedia)
    }

    private fun acquireStoragePermission() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            )
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_REQUEST_CODE
                )
        }
    }

    private fun onHasStoragePermission(): Boolean {
        context?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            )
                return false
        }
        return true
    }

    companion object {
        private const val MAIN_MEDIA_ID = "main-media"
        private const val STORAGE_REQUEST_CODE = 1
    }
}


