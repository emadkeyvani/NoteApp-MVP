package com.keyvani.noteappmvp.packages.di

import androidx.fragment.app.Fragment
import com.keyvani.noteappmvp.packages.ui.add.AddContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ContractsViewModuleFR {

    @Provides
    fun noteView(fragment: Fragment): AddContracts.View {
        return fragment as AddContracts.View
    }
}