package com.keyvani.noteappmvp.packages.data.repository

import com.keyvani.noteappmvp.packages.data.database.NoteDao
import com.keyvani.noteappmvp.packages.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {
    fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
    fun getAllNote() = dao.getAllNotes()
    fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
    fun detailNote(id: Int) = dao.getNote(id)
    fun editNote(entity: NoteEntity) = dao.updateNote(entity)
    fun filterNote(priority: String) = dao.filterNote(priority)
    fun searchNote(title: String) = dao.searchNote(title)
}