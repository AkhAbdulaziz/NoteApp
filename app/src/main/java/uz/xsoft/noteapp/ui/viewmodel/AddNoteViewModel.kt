package uz.xsoft.noteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import uz.xsoft.noteapp.data.database.AppDatabase
import uz.xsoft.noteapp.data.entity.NoteEntity

class AddNoteViewModel : ViewModel() {
    private val database = AppDatabase.getDatabase().getNoteDao()

    fun addNote(data: NoteEntity) {
        database.insert(data)
    }

    fun updateNote(data: NoteEntity) {
        database.update(data)
    }

    fun deleteNote(data: NoteEntity) {
        database.delete(data)
    }
}