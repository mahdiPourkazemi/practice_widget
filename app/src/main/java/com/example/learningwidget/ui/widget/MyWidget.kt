package com.example.learningwidget.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.TextStyle
import com.example.learningwidget.R
import com.example.learningwidget.ui.state.NoteState
import kotlinx.coroutines.coroutineScope

class MyWidget: GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(
            DpSize(180.dp, 180.dp), // اندازه کوچک
            DpSize(640.dp, 640.dp)  // اندازه بزرگ
        )
    )
    override val stateDefinition: GlanceStateDefinition<NoteState> = NoteStateDefinition
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            GlanceTheme {
                val noteState = currentState<NoteState>()
                Scaffold(
                    modifier = GlanceModifier
                        .background(GlanceTheme.colors.onSurface)
                        .fillMaxSize(),
                    titleBar = {
                        TitleBar(
                            startIcon = ImageProvider(R.drawable.oval_button_bg),
                            title = "My Notes"
                        )
                    }
                ) {
                    WidgetContent(noteState)
                }
            }
        }
    }
}

@Composable
fun WidgetContent(noteState: NoteState) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        when {
            noteState.loading -> {
                Text(
                    text = "Loading...",
                    style = TextStyle(
                        color = ColorProvider(Color.Gray,
                            Color.Black),
                        fontSize = 16.sp
                    )
                )
            }
            noteState.errorMessage.isNotEmpty() -> {
                Text(
                    text = noteState.errorMessage,
                    style = TextStyle(
                        color = ColorProvider(Color.Red,
                            Color.Black),
                        fontSize = 16.sp
                    )
                )
            }
            noteState.successNotes.isEmpty() -> {
                Text(
                    text = "No notes available",
                    style = TextStyle(
                        color = ColorProvider(Color.Black, Color.White),
                        fontSize = 16.sp
                    )
                )
            }
            else -> {
                // نمایش جدیدترین یادداشت
                val latestNote = noteState.successNotes.first()
                Column(
                    modifier = GlanceModifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Horizontal.Start
                ) {
                    Text(
                        text = latestNote.noteTitle,
                        style = TextStyle(
                            color = ColorProvider(Color.Black, Color.White),
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(8.dp))
                    Text(
                        text = latestNote.noteContent,
                        style = TextStyle(
                            color = ColorProvider(Color.DarkGray, Color.LightGray),
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(8.dp))
                    Text(
                        text = "Date: ${latestNote.noteDate}",
                        style = TextStyle(
                            color = ColorProvider(Color.Gray,Color.Black),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}