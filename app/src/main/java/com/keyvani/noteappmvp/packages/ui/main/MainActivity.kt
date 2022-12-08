package com.keyvani.noteappmvp.packages.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.keyvani.noteappmvp.databinding.ActivityMainBinding
import com.keyvani.noteappmvp.packages.ui.add.NoteFragment

class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            btnAddNote.setOnClickListener { NoteFragment().show(supportFragmentManager, NoteFragment().tag) }
        }
    }
}