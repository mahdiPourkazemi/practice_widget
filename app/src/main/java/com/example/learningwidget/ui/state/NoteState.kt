package com.example.learningwidget.ui.state

import kotlinx.serialization.Serializable


@Serializable
data class NoteState(
    val successNotes: List<Notes> = emptyList(),
    val errorMessage: String = "",
    val loading: Boolean = false
)
