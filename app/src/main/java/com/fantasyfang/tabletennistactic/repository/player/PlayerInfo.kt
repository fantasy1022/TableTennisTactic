package com.fantasyfang.tabletennistactic.repository.player

import androidx.compose.ui.geometry.Offset
import com.fantasyfang.tabletennistactic.data.player.Team

data class PlayerInfo(
    val id: Int?,
    val team: Team,
    val index: Int,
    val name: String,
    val offset: Offset,
)
