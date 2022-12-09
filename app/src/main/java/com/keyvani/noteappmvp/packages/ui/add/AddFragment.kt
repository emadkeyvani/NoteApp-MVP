package com.keyvani.noteappmvp.packages.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keyvani.noteappmvp.databinding.FragmentNoteBinding
import com.keyvani.noteappmvp.packages.data.model.NoteEntity
import com.keyvani.noteappmvp.packages.data.repository.NoteRepository
import com.keyvani.noteappmvp.packages.utils.BUNDLE_ID
import com.keyvani.noteappmvp.packages.utils.EDIT
import com.keyvani.noteappmvp.packages.utils.NEW
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddFragment : BottomSheetDialogFragment(), AddContracts.View {
    //Binding
    private lateinit var binding: FragmentNoteBinding

    @Inject
    lateinit var entity: NoteEntity

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var presenter: AddPresenter

    //Other
    private lateinit var categoriesList: Array<String>
    private lateinit var prioritiesList: Array<String>
    private var category = ""
    private var priority = ""
    private var noteID = 0
    private var type = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bundle
        noteID = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        type = if (noteID > 0) {
            EDIT
        } else {
            NEW
        }

        //InitViews
        binding.apply {
            //Close
            ivClose.setOnClickListener { this@AddFragment.dismiss() }
            //Spinners
            categoriesSpinnerItem()
            prioritySpinnerItem()
            //Set default value
            if (type == EDIT) {
                presenter.detailNote(noteID)
            }
            //Save
            btnSaveNote.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDescription.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()) {
                    //Entity
                    entity.id = noteID
                    entity.title = title
                    entity.desc = desc
                    entity.category = category
                    entity.priority = priority
                    //Save
                    if (type == NEW) {
                        presenter.saveNote(entity)
                    } else {
                        presenter.updateNote(entity)
                    }

                } else {
                    Toast.makeText(context, "Title and Description can not be empty", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun categoriesSpinnerItem() {
        categoriesList = arrayOf("Work", "Home", "Education", "Health")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnCategories.adapter = adapter
        binding.spnCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = categoriesList[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun prioritySpinnerItem() {
        prioritiesList = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prioritiesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnPriority.adapter = adapter
        binding.spnPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                priority = prioritiesList[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun close() {
        this.dismiss()
    }

    override fun loadNote(noteEntity: NoteEntity) {
        if (this.isAdded) {
            requireActivity().runOnUiThread {
                binding.apply {
                    edtTitle.setText(noteEntity.title)
                    edtDescription.setText(noteEntity.desc)
                    spnCategories.setSelection(getIndex(categoriesList, noteEntity.category))
                    spnPriority.setSelection(getIndex(prioritiesList, noteEntity.priority))
                }
            }
        }

    }

    private fun getIndex(list: Array<String>, item: String): Int {
        var index = 0
        for (i in list.indices) {
            if (list[i] == item) {
                index = i
                break
            }
        }
        return index
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }


}