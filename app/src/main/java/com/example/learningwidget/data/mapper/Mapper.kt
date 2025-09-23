package com.example.learningwidget.data.mapper

import com.example.learningwidget.data.NoteEntity
import com.example.learningwidget.ui.state.Notes

interface Mapper{

    // تبدیل Notes (UI) به NoteEntity (دیتابیس)
    fun toNoteEntity(notes: Notes): NoteEntity

    // تبدیل NoteEntity (دیتابیس) به Notes (UI)
    fun toNotes(noteEntity: NoteEntity): Notes

    // تبدیل لیست NoteEntity به لیست Notes
    fun toNotesList(entities: List<NoteEntity>): List<Notes>
    // متد برای دریافت تاریخ فعلی با فرمت استاندارد
}