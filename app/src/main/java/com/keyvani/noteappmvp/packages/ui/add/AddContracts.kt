package com.keyvani.noteappmvp.packages.ui.add

import com.keyvani.noteappmvp.packages.base.BasePresenter
import com.keyvani.noteappmvp.packages.data.model.NoteEntity

interface AddContracts {

    interface View {
        fun close()
        fun loadNote(noteEntity: NoteEntity)

    }

    interface Presenter : BasePresenter {
        fun saveNote(entity: NoteEntity)
        fun detailNote(id: Int)
        fun updateNote(entity: NoteEntity)

    }

}