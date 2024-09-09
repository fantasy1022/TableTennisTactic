package com.fantasyfang.tabletennistactic.data.player

import androidx.room.TypeConverter


enum class Team(val value: Int) {
    TEAM_1(1),
    TEAM_2(2),
}

class TeamTypeConverter {
    @TypeConverter
    fun fromTeam(team: Team): Int {
        return team.value
    }

    @TypeConverter
    fun toTeam(value: Int): Team {
        return Team.entries.first { it.value == value }
    }
}

