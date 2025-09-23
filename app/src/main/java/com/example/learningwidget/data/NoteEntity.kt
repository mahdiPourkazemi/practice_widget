package com.example.learningwidget.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    val isDone : Boolean,
    val noteTitle: String,
    val noteContent: String,
    val noteDate: String,
)