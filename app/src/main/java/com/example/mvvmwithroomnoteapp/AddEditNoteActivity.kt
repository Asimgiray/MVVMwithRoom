package com.task.noteapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmwithroomnoteapp.R
import com.example.mvvmwithroomnoteapp.databinding.ActivityAddEditNoteBinding
import com.example.mvvmwithroomnoteapp.db.Note
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.EXT1
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.EXT2
import com.example.mvvmwithroomnoteapp.utils.Utility.Companion.getCurrentDate
import com.example.mvvmwithroomnoteapp.utils.Utility.Companion.isNullOrEmpty
import com.example.mvvmwithroomnoteapp.utils.Utility.Companion.showError
import com.example.mvvmwithroomnoteapp.utils.dataBinding
import com.example.mvvmwithroomnoteapp.viewModel.NoteViewModel


class AddEditNoteActivity : AppCompatActivity() {

    private val binding: ActivityAddEditNoteBinding by dataBinding(R.layout.activity_add_edit_note)

    lateinit var currentType: Type
    private lateinit var currentNote: Note

    private lateinit var viewModel: NoteViewModel

    private lateinit var titleText: String
    private lateinit var descText: String
    private lateinit var imageText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        currentType = intent.getSerializableExtra(EXT1) as Type

        init()
        listeners()
    }

    private fun init() {

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        when (currentType) {
            Type.CREATE -> {
                initForCreate()
            }
            Type.EDIT -> {
                initForEdit()
            }
        }
    }

    private fun initForCreate() {
        binding.btnConfirm.text = resources.getString(R.string.add_note)

    }

    private fun initForEdit() {
        binding.btnConfirm.text = resources.getString(R.string.edit_note)
        currentNote = intent.getSerializableExtra(EXT2) as Note

        binding.edtTitle.setText(currentNote.noteTitle)
        binding.edtDesc.setText(currentNote.noteDesc)
        binding.edtImage.setText(currentNote.noteImage)
    }

    private fun listeners() {
        binding.btnConfirm.setOnClickListener {
            when (currentType) {
                Type.CREATE -> {
                    processForCreate()
                }
                Type.EDIT -> {
                    processForEdit()
                }
            }
        }
    }

    private fun processForCreate() {
        if (!checkEditTextFields()) {
            val newNote = Note(
                titleText,
                descText,
                imageText,
                getCurrentDate(),
                false)
            viewModel.addNote(newNote)
            finalize(newNote)
        } else {
            showError(this , resources.getString(R.string.field_required_alert))
        }
    }

    private fun processForEdit() {
        if (!checkEditTextFields()) {
            val newNote = Note(
                titleText,
                descText,
                imageText,
                getCurrentDate(),
                true)
            newNote.id = currentNote.id
            viewModel.updateNote(newNote)
            finalize(newNote)
        } else {
            showError(this , resources.getString(R.string.field_required_alert))
        }
    }

    private fun checkEditTextFields(): Boolean {
        titleText = binding.edtTitle.text.toString()
        descText = binding.edtDesc.text.toString()
        imageText = binding.edtImage.text.toString()

        return isNullOrEmpty(titleText) || isNullOrEmpty(descText)
    }

    private fun finalize(note: Note) {
        intent.putExtra(EXT1 , note)
        setResult(RESULT_OK , intent)
        finish()
    }

    companion object {
        @JvmStatic
            fun create(context: Context , type: Type) =
                Intent(context , AddEditNoteActivity::class.java)
                    .putExtra(EXT1 , type)

        @JvmStatic
            fun create(context: Context , type: Type , note: Note) =
                Intent(context , AddEditNoteActivity::class.java)
                    .putExtra(EXT1 , type)
                    .putExtra(EXT2 , note)
    }

}

enum class Type {
    CREATE , EDIT
}