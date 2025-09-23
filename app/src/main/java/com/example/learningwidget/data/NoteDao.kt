package com.example.learningwidget.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(noteEntity: NoteEntity )
    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)//todo fix this / it must only need ID
    @Update
    suspend fun updateNote(noteEntity: NoteEntity)
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>
}