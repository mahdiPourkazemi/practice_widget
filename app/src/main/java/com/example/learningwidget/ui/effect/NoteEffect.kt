package com.example.learningwidget.ui.effect

sealed class NoteEffect {
    data class ShowMessage(val message: String) : NoteEffect()
    object NavigateToNoteScreen : NoteEffect() //Todo maybe its work with new navigation system
    object NavigateToNoteDetailScreen : NoteEffect() //Todo maybe its work with new navigation system
}