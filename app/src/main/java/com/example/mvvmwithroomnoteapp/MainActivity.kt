package com.task.noteapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmwithroomnoteapp.R
import com.example.mvvmwithroomnoteapp.adapter.NoteAdapter
import com.example.mvvmwithroomnoteapp.databinding.ActivityMainBinding
import com.example.mvvmwithroomnoteapp.db.Note
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.EXT1
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.REQ_CREATE_NOTE
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.REQ_EDIT_NOTE
import com.example.mvvmwithroomnoteapp.utils.RowClickListener
import com.example.mvvmwithroomnoteapp.utils.dataBinding
import com.example.mvvmwithroomnoteapp.viewModel.NoteViewModel
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by dataBinding(R.layout.activity_main)
    private var viewModel: NoteViewModel? = null
    private var createActivityLauncher : ActivityResultLauncher<Intent>? = null
    private var editActivityLauncher : ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        listeners()
    }

    private fun init() {

        createActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            activityResult(REQ_CREATE_NOTE, result)
        }

        editActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            activityResult(REQ_EDIT_NOTE, result)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val deleteListener = object : RowClickListener<Serializable> {
            override fun onRowClick(row: Int, item: Serializable) {
                val note = item as Note
                showDeleteAlert(this@MainActivity, note)
            }
        }

        val noteClickListener = object : RowClickListener<Serializable> {
            override fun onRowClick(row: Int, item: Serializable) {
                val note = item as Note
                editActivityLauncher?.launch(AddEditNoteActivity.create(this@MainActivity, Type.EDIT, note))
            }
        }

        viewModel?.allNotes?.observe(this) { list ->
            list?.let {
                binding.rvNotes.adapter = NoteAdapter(list, deleteListener, noteClickListener)
            }
        }
    }

    private fun listeners() {

        binding.fabAddNote.setOnClickListener {
            createActivityLauncher?.launch(AddEditNoteActivity.create(this@MainActivity, Type.CREATE))
        }

    }

     private fun activityResult(requestCode: Int, result: ActivityResult) {
         if (result.resultCode == RESULT_OK) {
             val intent = result.data
             val item = intent?.getSerializableExtra(EXT1) as Note
             when (requestCode) {
                 REQ_EDIT_NOTE -> {
                     Toast.makeText(this, "You updated the note as ${item.noteTitle}", Toast.LENGTH_SHORT).show()
                 }
                 REQ_CREATE_NOTE -> {
                     Toast.makeText(this, "You successfully created the ${item.noteTitle} note", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }

    fun showDeleteAlert(context: Context,  note: Note)  {
        val builder = AlertDialog.Builder(context)
        val alertDialog: AlertDialog = builder
            .setTitle(R.string.confirm_delete)
            .setMessage("Are you sure you want to delete ${note.noteTitle} note?")
            .setPositiveButton("Yes"){ dialogInterface, _ ->
                viewModel?.deleteNote(note)
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
        alertDialog.show()
    }
}