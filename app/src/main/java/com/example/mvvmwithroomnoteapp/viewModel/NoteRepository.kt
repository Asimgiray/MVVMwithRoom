package com.example.mvvmwithroomnoteapp.viewModel

import androidx.lifecycle.LiveData
import com.example.mvvmwithroomnoteapp.db.Note
import com.example.mvvmwithroomnoteapp.db.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

}