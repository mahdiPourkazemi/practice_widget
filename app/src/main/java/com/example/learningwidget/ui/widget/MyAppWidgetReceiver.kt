package com.example.learningwidget.ui.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.example.learningwidget.data.repo.NoteRepository
import com.example.learningwidget.ui.state.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: MyWidget
        get() = MyWidget()

    @Inject
    lateinit var noteRepository: NoteRepository

    private var observeJob: Job? = null

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        // شروع مشاهده تغییرات دیتابیس وقتی اولین ویجت اضافه می‌شود
        observeNotes(context)
    }

    override fun onDisabled(context: Context) {
        // توقف مشاهده وقتی آخرین ویجت حذف می‌شود
        observeJob?.cancel()
        super.onDisabled(context)
    }

    private fun observeNotes(context: Context) {
        observeJob = CoroutineScope(Dispatchers.IO).launch {
            noteRepository.getAllNotes().collect { notes ->
                updateWidgetDataStore(context, notes)
            }
        }
    }

    private suspend fun updateWidgetDataStore(context: Context, notes: List<Notes>) {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(MyWidget::class.java)

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
                glanceAppWidget.update(context, glanceId)
            } catch (e: Exception) {
                dataStore.updateData { current ->
                    current.copy(
                        errorMessage = "Failed to update widget: ${e.message}",
                        loading = false
                    )
                }
                glanceAppWidget.update(context, glanceId)
            }
        }
    }
}
//class MyAppWidgetReceiver(override val glanceAppWidget: MyWidget) : GlanceAppWidgetReceiver() {
