package com.example.learningwidget.ui.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.example.learningwidget.di.MyApplication
import kotlinx.coroutines.flow.firstOrNull
private val NOTE_ID_KEY = ActionParameters.Key<Int>("note_id")

class DeleteNoteAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val noteId = parameters[NOTE_ID_KEY] ?: return
        val repository = (context.applicationContext as MyApplication).noteRepository
        val currentNotes = repository.getAllNotes().firstOrNull() ?: emptyList()
        val noteToDelete = currentNotes.find { it.noteId == noteId } ?: return

        repository.deleteNote(noteToDelete) // این تغییر به DataStore منتقل می‌شود
    }
}