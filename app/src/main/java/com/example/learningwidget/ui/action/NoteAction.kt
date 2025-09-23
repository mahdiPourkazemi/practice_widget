package com.example.learningwidget.ui.action

import com.example.learningwidget.ui.state.Notes


sealed class NoteAction {
    //object GetAllNotes : NoteAction()// i think is not right
    object Loading: NoteAction()
    data class NotesError(val error: String) : NoteAction()
    data class NotesSuccess(val notes: List<Notes>) : NoteAction()
}