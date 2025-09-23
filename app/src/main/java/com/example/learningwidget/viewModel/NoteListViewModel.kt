package com.example.learningwidget.viewModel

import androidx.lifecycle.viewModelScope
import com.example.learningwidget.data.repo.NoteRepository
import com.example.learningwidget.data.repo.NoteRepositoryImp
import com.example.learningwidget.ui.action.NoteAction
import com.example.learningwidget.ui.effect.NoteEffect
import com.example.learningwidget.ui.intent.NoteIntent
import com.example.learningwidget.ui.state.NoteState
import com.example.learningwidget.ui.state.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    val repository: NoteRepository
): BaseViewModel<NoteIntent, NoteState, NoteEffect, NoteAction>(
    NoteState()
) {
    init {
        loadNotes()  // برای بارگذاری اولیه
    }
    //this function handle all click
    override fun processIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.AddNote -> {
                launchWithResponse(
                    operation = {
                        setState { copy(loading = true) }
                        repository.addNote(
                            Notes(isDone = false,
                                noteTitle = intent.noteTitle,
                                noteContent = intent.noteContent,
                                noteDate = getCurrentDate())) },  // فرض: متد getCurrentDate
                    onSuccess = {
                        setEffect { NoteEffect.ShowMessage("Note added") }
                        setState { copy(loading = false) }
                        loadNotes()
                    },
                    onError = { errorType ->
                        setState { copy(errorMessage = errorType.getUserFriendlyMessage()) }
                    }
                )
            }
            is NoteIntent.UpdateNote -> {
                launchWithResponse(
                    operation = {
                        setState { copy(loading = true) }
                        repository.updateNote(
                            Notes(isDone = intent.isDone,
                                noteTitle = intent.noteTitle,
                                noteContent = intent.noteContent,
                                noteDate = getCurrentDate())) },
                    onSuccess = {
                        setEffect { NoteEffect.ShowMessage("Note updated") }
                        setState { copy(loading = false) }
                        loadNotes()
                    },
                    onError = { errorType ->
                        setState { copy(errorMessage = errorType.getUserFriendlyMessage()) }
                    }
                )
            }
            is NoteIntent.DeleteNote -> {
                launchWithResponse(
                    operation = {
                        setState { copy(loading = true) }
                        repository.deleteNote(intent.note) },
                    onSuccess = {
                        setEffect { NoteEffect.ShowMessage("Note deleted") }
                        setState { copy(loading = false) }
                        loadNotes()
                    },
                    onError = { errorType ->
                        setState { copy(errorMessage = errorType.getUserFriendlyMessage()) }
                    }
                )
            }
        }
    }

    private fun loadNotes() {
        repository.getAllNotes()
            .onStart {
                processAction(NoteAction.Loading)
            }.onEach { notes ->
                processAction(NoteAction.NotesSuccess(notes))
            }.catch { e ->
                processAction(NoteAction.NotesError(e.message ?: "Error loading notes"))
            }.launchIn(viewModelScope)
    }

    override fun processAction(action: NoteAction) {
        when (action) {
            is NoteAction.Loading -> {
                setState { copy(loading = true, errorMessage = "") }
            }
            is NoteAction.NotesSuccess -> {
                setState { copy(successNotes = action.notes, loading = false) }
            }
            is NoteAction.NotesError -> {
                setState { copy(errorMessage = action.error, loading = false) }
            }
        }
    }

    // Helper برای تاریخ (می‌توانید از java.time یا SimpleDateFormat استفاده کنید)
    private fun getCurrentDate(): String {
        return "2025-09-15"  // مثال؛ در واقعیت از DateFormatter استفاده کنید
    }
}
