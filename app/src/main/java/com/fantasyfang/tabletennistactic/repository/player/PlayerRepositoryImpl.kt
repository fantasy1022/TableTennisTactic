package com.fantasyfang.tabletennistactic.repository.player

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PlayerRepositoryImpl(
    private val itemDao: PlayerDao,
    private val playerMapper: PlayerMapper
) : PlayerRepository {
    override fun getAllPlayers(): Flow<List<PlayerInfo>> = itemDao.getAllItems().map { players ->
        players.map { player ->
            PlayerInfo(
                id = player.id,
                team = player.team,
                index = player.playerIndex,
                name = player.name,
                offset = Offset(player.positionX, player.positionY),
            )
        }
    }

    override suspend fun insertPlayer(playerInfo: PlayerInfo) {
        itemDao.insert(playerMapper.toPlayer(playerInfo))
    }

    override suspend fun deletePlayer(playerId: Int) = itemDao.delete(playerId)

    override suspend fun updatePlayer(playerInfo: PlayerInfo) {
        itemDao.insert(playerMapper.toPlayer(playerInfo))
    }

    override suspend fun updatePlayerPosition(playerId: Int, newPosition: Offset) {
        val player = itemDao.getItem(playerId).first() ?: return
        val updatedPlayer = player.copy(positionX = newPosition.x, positionY = newPosition.y)
        itemDao.update(updatedPlayer)
    }
}