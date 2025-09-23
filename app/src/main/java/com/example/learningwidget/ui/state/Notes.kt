package com.example.learningwidget.ui.state

import kotlinx.serialization.Serializable

//for example for some data like time may it be useful to separate here but on data base come together
// and it is a good practice to have different type of data for ui and data base
@Serializable
data class Notes(
    val noteId: Int=0,
    val isDone : Boolean,
    val noteTitle: String,
    val noteContent: String,
    val noteDate: String,
)
