package uz.xsoft.noteapp.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.xsoft.noteapp.app.App
import uz.xsoft.noteapp.data.dao.NoteDao
import uz.xsoft.noteapp.data.entity.NoteEntity

@Database(entities = [NoteEntity::class],version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao

    companion object {
        private lateinit var database : AppDatabase

        fun getDatabase() : AppDatabase {
            if (!Companion::database.isInitialized) {
                database = Room.databaseBuilder(App.instance, AppDatabase::class.java,"NoteApp.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return database
        }
    }
}
