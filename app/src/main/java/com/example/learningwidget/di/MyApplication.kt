package com.example.learningwidget.di

import android.app.Application
import com.example.learningwidget.data.repo.NoteRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {
    @Inject
    lateinit var noteRepository: NoteRepository

    override fun onCreate() {
        super.onCreate()
    }
}