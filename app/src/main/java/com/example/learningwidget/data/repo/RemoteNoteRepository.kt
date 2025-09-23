package com.example.learningwidget.data.repo

/*
import javax.inject.Inject

class RemoteNoteRepository @Inject constructor(
    private val noteApi: NoteApi, // فرض: یک interface برای API
    private val noteMapper: NoteMapper
) : NoteRepository {
    override suspend fun addNote(notes: Notes): Result<Unit> {
        return try {
            noteApi.addNote(noteMapper.toNoteApiModel(notes))
            Result.Success(Unit)
        } catch (e: HttpException) {
            Result.Failure(ErrorType.NetworkError)
        } catch (e: Exception) {
            Result.Failure(ErrorType.UnknownError(e.message ?: "Unknown error"))
        }
    }
    // بقیه متدها
}*/
