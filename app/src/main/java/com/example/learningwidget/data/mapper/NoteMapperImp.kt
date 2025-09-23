package com.example.learningwidget.data.mapper

import com.example.learningwidget.data.NoteEntity
import com.example.learningwidget.ui.state.Notes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject



class NoteMapperImp @Inject constructor(): Mapper {

    override fun toNoteEntity(notes: Notes): NoteEntity {
        return NoteEntity(
            noteId = notes.noteId,
            isDone = notes.isDone,
            noteTitle = notes.noteTitle,
            noteContent = notes.noteContent,
            noteDate = notes.noteDate.takeIf { it.isNotBlank() } ?: getCurrentDate()
        )
    }

    override fun toNotes(noteEntity: NoteEntity): Notes {
        return Notes(
            noteId = noteEntity.noteId,
            isDone = noteEntity.isDone,
            noteTitle = noteEntity.noteTitle,
            noteContent = noteEntity.noteContent,
            noteDate = noteEntity.noteDate
        )
    }

    override fun toNotesList(entities: List<NoteEntity>): List<Notes> {
        return entities.map { toNotes(it) }
    }

    private fun getCurrentDate(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDateTime.format(formatter)
    }

}