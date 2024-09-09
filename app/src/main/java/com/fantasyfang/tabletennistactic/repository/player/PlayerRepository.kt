package com.fantasyfang.tabletennistactic.repository.player

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getAllPlayers(): Flow<List<PlayerInfo>>
    suspend fun insertPlayer(playerInfo: PlayerInfo)
    suspend fun updatePlayer(playerInfo: PlayerInfo)
    suspend fun deletePlayer(playerId: Int)
    suspend fun updatePlayerPosition(playerId: Int, newPosition: Offset)
}