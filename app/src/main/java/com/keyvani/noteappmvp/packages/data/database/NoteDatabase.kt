package com.keyvani.noteappmvp.packages.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keyvani.noteappmvp.packages.data.model.NoteEntity

@Database(entities =[NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase() {
    abstract fun noteDao() : NoteDao
}