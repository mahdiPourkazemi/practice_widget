package com.example.learningwidget.utils

import android.content.Context
import androidx.datastore.dataStore
import com.example.learningwidget.ui.widget.NoteStateDefinition.NoteStateSerializer

const val DATA_STORE_FILENAME_PREFIX = "NoteState_"
val Context.datastore by dataStore(DATA_STORE_FILENAME_PREFIX, NoteStateSerializer)
