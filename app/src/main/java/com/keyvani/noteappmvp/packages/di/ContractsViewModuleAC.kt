package com.keyvani.noteappmvp.packages.di

import android.app.Activity
import com.keyvani.noteappmvp.packages.ui.main.MainContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ContractsViewModuleAC {

    @Provides
    fun mainView(activity: Activity): MainContracts.View {
        return activity as MainContracts.View

    }
}