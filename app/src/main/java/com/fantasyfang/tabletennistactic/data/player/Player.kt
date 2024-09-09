package com.fantasyfang.tabletennistactic.data.player

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "players",
)
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerIndex: Int,
    val team: Team,
    val name: String,
    val positionX: Float,
    val positionY: Float,
)
