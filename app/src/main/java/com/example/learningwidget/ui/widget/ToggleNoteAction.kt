package com.example.learningwidget.ui.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.example.learningwidget.di.MyApplication
import com.example.learningwidget.data.repo.NoteRepository
import com.example.learningwidget.ui.state.Notes
import kotlinx.coroutines.flow.firstOrNull
private val NOTE_ID_KEY = ActionParameters.Key<Int>("note_id")

class ToggleNoteAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val noteId = parameters[NOTE_ID_KEY] ?: return
        val repository = (context.applicationContext as MyApplication).noteRepository
        val currentNotes = repository.getAllNotes().firstOrNull() ?: emptyList()
        val noteToToggle = currentNotes.find { it.noteId == noteId } ?: return

        val updatedNote = noteToToggle.copy(isDone = !noteToToggle.isDone)
        repository.updateNote(updatedNote) // این تغییر به DataStore منتقل می‌شود
    }
}
