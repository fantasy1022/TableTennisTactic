package com.fantasyfang.tabletennistactic.util

import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.ui.theme.FloorColorList
import com.fantasyfang.tabletennistactic.ui.theme.BrushColorList
import com.fantasyfang.tabletennistactic.ui.theme.TableColorList
import com.fantasyfang.tabletennistactic.ui.theme.TeamColorList

class Const {
    companion object {
        const val DEFAULT_TABLE_SCREEN_WIDTH_RATIO = 0.65f
        const val TABLE_HEIGHT_WIDTH_RATIO = 1.79f //Height: 274cm, Width: 152.5cm
        const val MAX_PLAYER_INDEX = 2

        val DEFAULT_IS_PREVENT_SLEEP = true
        val DEFAULT_BRUSH_WIDTH = 10f
        val DEFAULT_BRUSH_COLOR = BrushColorList.last()
        val DEFAULT_TABLE_COLOR = TableColorList[0] //TODO: change name to DEFAULT_TABLE_COLOR
        val DEFAULT_FLOOR_COLOR = FloorColorList[0]
        val DEFAULT_TEAM_1_COLOR = TeamColorList[0]
        val DEFAULT_TEAM_2_COLOR = TeamColorList[1]
        val PLAYER_ICON_RADIUS_MIN = 10.dp
        val PLAYER_ICON_RADIUS_MAX = 30.dp
        val PLAYER_ICON_RADIUS_INTERVAL = PLAYER_ICON_RADIUS_MAX - PLAYER_ICON_RADIUS_MIN
        val DEFAULT_PLAYER_ICON_RADIUS =
            (PLAYER_ICON_RADIUS_MIN + PLAYER_ICON_RADIUS_MAX) / 2
        val DEFAULT_IS_SHOW_PLAYER_NAME = true
    }
}