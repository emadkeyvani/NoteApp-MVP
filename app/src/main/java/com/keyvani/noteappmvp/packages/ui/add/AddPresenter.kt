package com.keyvani.noteappmvp.packages.ui.add

import com.keyvani.noteappmvp.packages.base.BasePresenterImpl
import com.keyvani.noteappmvp.packages.data.model.NoteEntity
import com.keyvani.noteappmvp.packages.data.repository.NoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddPresenter @Inject constructor(private val repository: NoteRepository, private val view: AddContracts.View) : AddContracts.Presenter, BasePresenterImpl() {


    override fun saveNote(entity: NoteEntity) {
        disposable = repository.saveNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }

    override fun detailNote(id: Int) {
        disposable = repository.detailNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.loadNote(it)

            }
    }

    override fun updateNote(entity: NoteEntity) {
        disposable = repository.editNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }
}