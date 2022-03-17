package com.example.mvvmwithroomnoteapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvmwithroomnoteapp.utils.Constants.Companion.tableName
import java.io.Serializable

@Entity (tableName = tableName)

class Note (
    @ColumnInfo(name = "noteTitle") val noteTitle: String,
    @ColumnInfo(name = "noteDesc") val noteDesc: String,
    @ColumnInfo(name = "noteImage") val noteImage: String,
    @ColumnInfo(name = "date") val noteDate: String,
    @ColumnInfo(name = "isEdited") val isNoteEdited: Boolean
) : Serializable {
    @PrimaryKey(autoGenerate = true) var id = 0

}
