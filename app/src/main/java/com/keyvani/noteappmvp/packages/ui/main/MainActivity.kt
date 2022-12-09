package com.keyvani.noteappmvp.packages.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.keyvani.noteappmvp.R
import com.keyvani.noteappmvp.databinding.ActivityMainBinding
import com.keyvani.noteappmvp.packages.adapter.MainAdapter
import com.keyvani.noteappmvp.packages.data.model.NoteEntity
import com.keyvani.noteappmvp.packages.data.repository.NoteRepository
import com.keyvani.noteappmvp.packages.ui.add.AddFragment
import com.keyvani.noteappmvp.packages.utils.BUNDLE_ID
import com.keyvani.noteappmvp.packages.utils.DELETE
import com.keyvani.noteappmvp.packages.utils.EDIT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {
    //Binding
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var mainAdapter: MainAdapter

    @Inject
    lateinit var presenter: MainPresenter

    //Other
    private var selectedPriority = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //InitViews
        binding.apply {
            //Set action view
            setSupportActionBar(notesToolbar)
            //Note detail
            btnAddNote.setOnClickListener { AddFragment().show(supportFragmentManager, AddFragment().tag) }
            //Load Notes
            presenter.getAllNotes()
            //Clicks
            mainAdapter.setonItemClickListener { noteEntity, state ->
                when (state) {
                    EDIT -> {
                        val addFragment = AddFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID, noteEntity.id)
                        addFragment.arguments = bundle
                        addFragment.show(supportFragmentManager, AddFragment().tag)

                    }
                    DELETE -> {
                        val entity = NoteEntity(
                            noteEntity.id, noteEntity.title, noteEntity.desc,
                            noteEntity.category, noteEntity.priority
                        )
                        presenter.deleteNote(entity)

                    }
                }
            }
            //Filter
            notesToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionFilter -> {
                        filterByPriority()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false

                    }
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchNote(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showAllNotes(notes: List<NoteEntity>) {
        binding.conEmpty.visibility = View.GONE
        binding.rvNoteList.visibility = View.VISIBLE
        mainAdapter.setData(notes)
        binding.rvNoteList.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mainAdapter
        }
    }

    override fun showEmpty() {
        binding.conEmpty.visibility = View.VISIBLE
        binding.rvNoteList.visibility = View.GONE
    }

    override fun deleteMassage() {

        Snackbar.make(binding.root, "Item Deleted!", Snackbar.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun filterByPriority() {
        val builder = AlertDialog.Builder(this)
        val priorities = arrayOf("All", "High", "Medium", "Low")
        builder.setSingleChoiceItems(priorities, selectedPriority) { dialog, item ->
            when (item) {
                0 -> {
                    presenter.getAllNotes()
                }
                in 1..3 -> {
                    presenter.filterNote(priorities[item])
                }

            }
            selectedPriority = item
            dialog.dismiss()

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }
}