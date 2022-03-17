package com.example.mvvmwithroomnoteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.tableName

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote (note: Note)

    @Delete
    suspend fun deleteNote (note: Note)

    @Query ("Select * from $tableName order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote (note: Note)
}