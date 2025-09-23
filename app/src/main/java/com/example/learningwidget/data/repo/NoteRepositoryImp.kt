package com.example.learningwidget.data.repo

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.learningwidget.data.NoteDao
import com.example.learningwidget.data.NoteEntity
import com.example.learningwidget.data.mapper.Mapper
import com.example.learningwidget.data.mapper.NoteMapperImp
import com.example.learningwidget.data.mapper.Response
import com.example.learningwidget.ui.state.Notes
import com.example.learningwidget.ui.widget.MyWidget
import com.example.learningwidget.ui.widget.NoteStateDefinition
import com.example.learningwidget.utils.ErrorType
import com.example.learningwidget.utils.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(
    private val noteMapper: Mapper,
    private val noteDao: NoteDao,
    private val context: Context
    ): NoteRepository {

    //todo do something about response<unit>
    override suspend fun addNote(notes: Notes): Response<Unit> {
            return try {
                if (notes.noteTitle.isBlank()) {
                    Response.Failure(ErrorType.ValidationError("Title cannot be empty"))
                } else {
                    noteDao.addNote(noteMapper.toNoteEntity(notes))
                    Response.Success(Unit)
                }
            } catch (e: Exception) {
                Response.Failure(ErrorType.DatabaseError(cause = e))
            }
    }

    override suspend fun updateNote(notes: Notes): Response<Unit> {
        return try {
            if (notes.noteTitle.isBlank()) {
                Response.Failure(ErrorType.ValidationError("Title cannot be empty"))
            } else {
                noteDao.updateNote(noteMapper.toNoteEntity(notes))
                Response.Success(Unit)
            }
        } catch (e: Exception) {
            Response.Failure(ErrorType.DatabaseError(cause = e))
        }
    }

    override suspend fun deleteNote(notes: Notes): Response<Unit> {
        return try {
            noteDao.deleteNote(noteMapper.toNoteEntity(notes))
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure(ErrorType.DatabaseError(cause = e))
        }
    }

    override fun getAllNotes(): Flow<List<Notes>> {
        return noteDao.getAllNotes().map { noteMapper.toNotesList(it) }
    }

    private suspend fun updateWidgetDataStore() {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(MyWidget::class.java)
        val notes = getAllNotes().firstOrNull() ?: emptyList()

        glanceIds.forEach { glanceId ->
            val dataStore = NoteStateDefinition.getDataStore(context, glanceId.toString())
            try {
                dataStore.updateData { current ->
                    current.copy(
                        successNotes = notes,
                        errorMessage = "",
                        loading = false
                    )
                }
                MyWidget().update(context, glanceId)
            } catch (e: Exception) {
                dataStore.updateData { current ->
                    current.copy(
                        errorMessage = "Failed to update widget: ${e.message}",
                        loading = false
                    )
                }
                MyWidget().update(context, glanceId)
            }
        }
    }
}