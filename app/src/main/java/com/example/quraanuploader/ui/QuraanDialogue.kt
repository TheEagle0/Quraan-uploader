package com.example.quraanuploader.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.quraanuploader.R
import com.example.quraanuploader.view_containers.MediaFragment
import com.jakewharton.rxbinding2.widget.RxTextView

class ShowSelectionDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getInt("title")
        val list = arguments?.getStringArrayList("list")
        val dialog = AlertDialog.Builder(context!!)
        title?.let { dialog.setTitle(title) }
        val arrayAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1)
        arrayAdapter.addAll(list!!.toMutableList())
        dialog.setAdapter(arrayAdapter,
            object : DialogInterface.OnCancelListener, DialogInterface.OnClickListener {
                override fun onCancel(dialog: DialogInterface) = dialog.dismiss()
                override fun onClick(dialog: DialogInterface, which: Int) {
                    (targetFragment as MediaFragment).doOnItemSelection(list[which])
                }
            })
        return dialog.create()
    }

    fun createNewDialog(@StringRes title: Int?, list: List<String>): ShowSelectionDialog {
        val dialog = ShowSelectionDialog()
        val args = Bundle()
        args.putInt("title", title!!)
        args.putStringArrayList("list", list as ArrayList<String>)
        dialog.arguments = args
        return dialog
    }

}

fun Fragment.showEditTextDialog(hint:String,
    onDoneClick: (String) -> Unit,
    onTextChanges: (String, AppCompatButton) -> Unit
): AlertDialog? {
    return context?.let {
        val dialogBuilder = AlertDialog.Builder(it)
        dialogBuilder.setView(R.layout.edit_text_dialog)
        val dialog = dialogBuilder.show()
        val textView = dialog.findViewById<AppCompatEditText>(R.id.title)!!
        textView.setText(hint)
        with(textView) { setSelection(text!!.length);requestFocus() }
        val doneButton = dialog.findViewById<AppCompatButton>(R.id.done)!!
        doneButton.setOnClickListener { textView.clearFocus();dialog.dismiss();onDoneClick("${textView.text}") }
        val disposable = RxTextView.textChanges(textView).map { text -> "$text" }
            .subscribe { text -> onTextChanges(text, doneButton) }
        dialogBuilder.setOnDismissListener { disposable.dispose() }
        return dialog
    }
}

class DeletionDialog(private val onClick: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(getString(R.string.delete_item))
        builder.setMessage(getString(R.string.deletion_message))
        builder.setPositiveButton(
            getString(R.string.delete)
        ) { _, _ -> onClick() }
        return builder.create()
    }
}