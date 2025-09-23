package com.example.learningwidget.ui.widget


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.example.learningwidget.ui.state.NoteState
import com.example.learningwidget.utils.DATA_STORE_FILENAME_PREFIX
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object NoteStateDefinition : GlanceStateDefinition<NoteState> {

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<NoteState> {
        return DataStoreFactory.create(
            serializer = NoteStateSerializer,
            produceFile = { getLocation(context, fileKey) }
        )
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile("$DATA_STORE_FILENAME_PREFIX${fileKey.lowercase()}")
    }

    object NoteStateSerializer : Serializer<NoteState> {
        override val defaultValue: NoteState = NoteState()

        override suspend fun readFrom(input: InputStream): NoteState = try {
            Json.decodeFromString(
                NoteState.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }

        override suspend fun writeTo(t: NoteState, output: OutputStream) {
            output.use {
                it.write(
                    Json.encodeToString(NoteState.serializer(), t).encodeToByteArray()
                )
            }
        }
    }
}
