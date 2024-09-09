package com.fantasyfang.tabletennistactic.repository.player

import androidx.compose.ui.geometry.Offset
import com.fantasyfang.tabletennistactic.data.player.Player

class PlayerMapper {

    fun toPlayer(playerInfo: PlayerInfo): Player {
        return if (playerInfo.id != null) {
            Player(
                id = playerInfo.id,
                team = playerInfo.team,
                playerIndex = playerInfo.index,
                name = playerInfo.name,
                positionX = playerInfo.offset.x,
                positionY = playerInfo.offset.y,
            )
        } else {
            Player(
                team = playerInfo.team,
                playerIndex = playerInfo.index,
                name = playerInfo.name,
                positionX = playerInfo.offset.x,
                positionY = playerInfo.offset.y,
            )
        }
    }

    fun toPlayerInfo(player: Player): PlayerInfo {
        return PlayerInfo(
            id = player.id,
            team = player.team,
            index = player.playerIndex,
            name = player.name,
            offset = Offset(player.positionX, player.positionY)
        )
    }
}