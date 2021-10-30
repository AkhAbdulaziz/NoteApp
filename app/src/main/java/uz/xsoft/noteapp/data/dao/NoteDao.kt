package uz.xsoft.noteapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.xsoft.noteapp.data.entity.NoteEntity

@Dao
interface NoteDao : BaseDao<NoteEntity> {

    @Query("SELECT * FROM NoteEntity WHERE isRemoved = 0 ORDER BY isPinned DESC, time DESC ")
    fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE isRemoved = 0 AND (title LIKE :text OR message LIKE :text) ORDER BY isPinned DESC, time DESC")
    fun searchNote(text: String): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE isRemoved = 1 ORDER BY isPinned DESC, time DESC ")
    fun getAllRemovedNotes(): List<NoteEntity>
}