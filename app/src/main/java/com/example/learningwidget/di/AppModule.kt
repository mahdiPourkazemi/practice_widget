package com.example.learningwidget.di

import android.content.Context
import androidx.room.Room
import com.example.learningwidget.data.NoteDao
import com.example.learningwidget.data.NoteDatabase
import com.example.learningwidget.data.mapper.Mapper
import com.example.learningwidget.data.mapper.NoteMapperImp
import com.example.learningwidget.data.repo.NoteRepository
import com.example.learningwidget.data.repo.NoteRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = NoteDatabase::class.java,
            name = "note_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase :NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }
    @Singleton
    @Provides
    fun provideNoteRepositoryImp(
        noteDao: NoteDao,
        noteMapper: Mapper,
        @ApplicationContext context: Context
    ) : NoteRepository {
        return NoteRepositoryImp(noteMapper,noteDao,context)
    }
    @Singleton
    @Provides
    fun provideNoteMapper(): Mapper {
        return NoteMapperImp()
    }

}