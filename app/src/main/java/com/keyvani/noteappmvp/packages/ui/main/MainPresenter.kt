package com.keyvani.noteappmvp.packages.ui.main

import com.keyvani.noteappmvp.packages.base.BasePresenterImpl
import com.keyvani.noteappmvp.packages.data.model.NoteEntity
import com.keyvani.noteappmvp.packages.data.repository.NoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val repository: NoteRepository, private val view: MainContracts.View) :
    MainContracts.Presenter, BasePresenterImpl() {
    override fun getAllNotes() {
        disposable = repository.getAllNote()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showAllNotes(it)
                } else {
                    view.showEmpty()
                }
            }

    }

    override fun deleteNote(entity: NoteEntity) {
        disposable = repository.deleteNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.deleteMassage()
            }
    }

    override fun filterNote(priority: String) {
        disposable = repository.filterNote(priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showAllNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }

    override fun searchNote(title: String) {
        disposable = repository.searchNote(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showAllNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }


}