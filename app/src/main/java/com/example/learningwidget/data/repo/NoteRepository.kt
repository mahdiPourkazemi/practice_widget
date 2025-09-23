package com.example.learningwidget.data.repo

import com.example.learningwidget.data.mapper.Response
import com.example.learningwidget.ui.state.Notes
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(notes: Notes): Response<Unit>
    suspend fun updateNote(notes: Notes): Response<Unit>
    suspend fun deleteNote(notes: Notes): Response<Unit>
    fun getAllNotes(): Flow<List<Notes>>
}