package uz.xsoft.noteapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import uz.xsoft.noteapp.data.database.AppDatabase
import uz.xsoft.noteapp.data.entity.NoteEntity

class NoteViewModel : ViewModel() {
    private val _noteListLiveData = MutableLiveData<List<NoteEntity>>()
    val noteListLiveData : LiveData<List<NoteEntity>> get() = _noteListLiveData

    private val database = AppDatabase.getDatabase().getNoteDao()

    fun loadData() {
        Timber.tag("TTT").d("loadData")
        _noteListLiveData.value = database.getAllNotes()
    }

    fun searchNote(text:String) : List<NoteEntity>{
      return  database.searchNote(text)
    }

    fun getAllNotes() : List<NoteEntity>{
        return database.getAllNotes()
    }

    fun removeData(data: NoteEntity){
        database.delete(data)
    }

    fun updateData(data:NoteEntity){
        database.update(data)
    }
}

