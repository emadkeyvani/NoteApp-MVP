package com.keyvani.noteappmvp.packages.ui.main

import com.keyvani.noteappmvp.packages.base.BasePresenter
import com.keyvani.noteappmvp.packages.data.model.NoteEntity

interface MainContracts {
    interface View {
        fun showAllNotes(notes: List<NoteEntity>)
        fun showEmpty()
        fun deleteMassage()

    }

    interface Presenter : BasePresenter {
        fun getAllNotes()
        fun deleteNote(entity: NoteEntity)
        fun filterNote(priority: String)
        fun searchNote(title: String)

    }
}