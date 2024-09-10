package com.fantasyfang.tabletennistactic.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepository
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepositoryImpl
import com.fantasyfang.tabletennistactic.repository.player.PlayerDatabase
import com.fantasyfang.tabletennistactic.repository.player.PlayerMapper
import com.fantasyfang.tabletennistactic.repository.player.PlayerRepository
import com.fantasyfang.tabletennistactic.repository.player.PlayerRepositoryImpl
import com.fantasyfang.tabletennistactic.repository.setting.SettingRepository
import com.fantasyfang.tabletennistactic.repository.setting.SettingRepositoryImpl
import com.fantasyfang.tabletennistactic.ui.setting.SettingViewModel
import com.fantasyfang.tabletennistactic.ui.tactic.TacticViewModel
import com.fantasyfang.tabletennistactic.usecase.setting.GetSettingsUseCase
import com.fantasyfang.tabletennistactic.usecase.setting.SetSettingsUseCase
import com.fantasyfang.tabletennistactic.usecase.tactic.GetTacticUseCase
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val appModule = module {
    singleOf(::PlayerMapper)
    single { PlayerDatabase.getDatabase(androidContext()).playerDao() }
    single<DataStore<Preferences>> { androidContext().dataStore }

    singleOf(::PlayerRepositoryImpl) { bind<PlayerRepository>() }
    singleOf(::BrushRepositoryImpl) { bind<BrushRepository>() }
    singleOf(::SettingRepositoryImpl) { bind<SettingRepository>() }

    factoryOf(::GetTacticUseCase)
    factoryOf(::SetTacticUseCase)
    viewModelOf(::TacticViewModel)

    factoryOf(::GetSettingsUseCase)
    factoryOf(::SetSettingsUseCase)
    viewModelOf(::SettingViewModel)
}
private const val USER_PREFERENCE_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)
