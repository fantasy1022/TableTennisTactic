package com.fantasyfang.tabletennistactic.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepository
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepositoryImpl
import com.fantasyfang.tabletennistactic.ui.tactic.TacticViewModel
import com.fantasyfang.tabletennistactic.usecase.tactic.GetTacticUseCase
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::BrushRepositoryImpl) { bind<BrushRepository>() }
    single<DataStore<Preferences>> { androidContext().dataStore }

    factoryOf(::GetTacticUseCase)
    factoryOf(::SetTacticUseCase)
    viewModelOf(::TacticViewModel)

}
private const val USER_PREFERENCE_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)
