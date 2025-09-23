package com.example.learningwidget.ui.intent

import com.example.learningwidget.ui.state.Notes

sealed class NoteIntent {
    //object GetNotes : NoteIntent()// this is not from user //suspend function
    //todo note date must be added in some mapper or somewhere else
    data class AddNote(val noteTitle: String, val noteContent:String) : NoteIntent()
    data class UpdateNote(val isDone: Boolean, val noteTitle: String, val noteContent:String) : NoteIntent()
    data class DeleteNote(val note: Notes) : NoteIntent()

    //data class NavigateTo(val rout:String): NoteIntent()

}