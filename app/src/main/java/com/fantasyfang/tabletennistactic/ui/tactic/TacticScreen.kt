package com.fantasyfang.tabletennistactic.ui.tactic

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.data.player.Team
import com.fantasyfang.tabletennistactic.extension.SetStatusSystemBarColor
import com.fantasyfang.tabletennistactic.extension.getScreenHeightInDp
import com.fantasyfang.tabletennistactic.extension.getScreenWidthInDp
import com.fantasyfang.tabletennistactic.extension.toPx
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo
import com.fantasyfang.tabletennistactic.ui.component.dialog.BrushWidthDialog
import com.fantasyfang.tabletennistactic.ui.component.dialog.ColorSelectDialog
import com.fantasyfang.tabletennistactic.ui.component.dialog.PlayerSettingDialog
import com.fantasyfang.tabletennistactic.ui.component.dialog.TeamSelectionDialog
import com.fantasyfang.tabletennistactic.ui.component.view.BallView
import com.fantasyfang.tabletennistactic.ui.component.view.DrawingView
import com.fantasyfang.tabletennistactic.ui.component.view.FloorView
import com.fantasyfang.tabletennistactic.ui.component.view.PlayerView
import com.fantasyfang.tabletennistactic.ui.component.view.SettingBarView
import com.fantasyfang.tabletennistactic.ui.component.view.TennisTableView
import com.fantasyfang.tabletennistactic.ui.theme.BrushColorList
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.BrushColor
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.BrushWidth
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.PlayerInsert
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.PlayerPosition
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.PlayerUpdate
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEBOUNCER_DELAY
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BALL_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BALL_RADIUS
import com.fantasyfang.tabletennistactic.util.Const.Companion.MAX_PLAYER_INDEX
import com.fantasyfang.tabletennistactic.util.Debouncer
import com.fantasyfang.tabletennistactic.util.DrawMode
import com.fantasyfang.tabletennistactic.util.NavigationRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun TacticScreen(
    navController: NavController,
    mainViewModel: TacticViewModel = koinViewModel(),
) {
    val uiState = mainViewModel.uiState.collectAsState().value
    var drawMode by remember { mutableStateOf(DrawMode.Touch) }
    val savedPaths by mainViewModel.paths.collectAsState()
    val paths by remember { mutableStateOf(savedPaths) }
    var showBrushWidthDialog by remember { mutableStateOf(false) }
    var showBrushColorDialog by remember { mutableStateOf(false) }
    var showTeamDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val debouncer = remember { Debouncer(coroutineScope) }
    var showPlayerDialog by remember { mutableStateOf(false) }
    var selectedPlayerId by remember { mutableStateOf<Int?>(null) }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (settingIconView, drawingScreen) = createRefs()

        val settingIconViewModifier = Modifier
            .navigationBarsPadding()
            .constrainAs(settingIconView) {
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }


        val drawingScreenModifier = Modifier.constrainAs(drawingScreen) {
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(settingIconView.top)
        }

        SetStatusSystemBarColor(uiState.floorColor)

        FloorView(
            color = uiState.floorColor,
        )
        TennisTableView(
            backgroundColor = uiState.tableColor
        )

        DrawingView(
            modifier = drawingScreenModifier,
            uiState = uiState,
            drawMode = drawMode,
            paths = paths,
        )

        SettingBarView(settingIconViewModifier,
            uiState = uiState,
            drawMode = drawMode,
            onSettingIconClick = {
                navController.navigate(NavigationRoute.SETTING.route)
            },
            onEditIconClick = {
                drawMode = if (drawMode != DrawMode.Draw) DrawMode.Draw else DrawMode.Touch
            },
            onBrushWidthClick = {
                showBrushWidthDialog = true
            },
            onBrushColorClick = {
                showBrushColorDialog = true
            },
            onEraseIconClick = {
                drawMode = if (drawMode != DrawMode.Erase) DrawMode.Erase else DrawMode.Touch
            },
            onPathUndoClick = {
                paths.undo()
            },
            onPathRedoClick = {
                paths.redo()
            },
            onPathClearClick = {
                paths.clear()
            },
            onAddPlayerClick = {
                showTeamDialog = true
            })

        BallView(
            ballRadius = DEFAULT_BALL_RADIUS,
            ballColor = DEFAULT_BALL_COLOR,
        )

        uiState.player.forEachIndexed { index, playerInfo ->
            val backgroundColor = if (playerInfo.team == Team.TEAM_1) {
                uiState.team1Color
            } else {
                uiState.team2Color
            }
            PlayerView(
                playerInfo = playerInfo,
                backgroundColor = backgroundColor,
                playerRadius = uiState.playerIconRadius,
                isShowPlayerName = uiState.isShowPlayerName,
                initialPosition = uiState.player[index].offset,
                onPositionChange = { position ->
                    debouncer.debounce(DEBOUNCER_DELAY) {
                        playerInfo.id?.let {
                            mainViewModel.updateSetting(
                                PlayerPosition(it, position)
                            )
                        }
                    }
                },
                onClick = {
                    Log.d("Fam", "PlayerView onClick: ${playerInfo.id}")
                    Log.d("Fam", "PlayerView onClick selectedPlayerId: $selectedPlayerId")
                    selectedPlayerId = playerInfo.id
                    showPlayerDialog = true
                },
                onDoubleClick = {
                    playerInfo.id?.let {
                        mainViewModel.updateSetting(SetTacticType.PlayerDelete(it))
                    }
                })
        }

        if (showBrushWidthDialog) {
            BrushWidthDialog(
                brushColor = uiState.brushColor,
                brushSize = uiState.brushWidth,
                onBrushSizeChange = { width ->
                    mainViewModel.updateSetting(BrushWidth(width))
                }, onDismissRequest = { showBrushWidthDialog = false })
        }

        if (showBrushColorDialog) {
            ColorSelectDialog(
                titleId = R.string.brush_color_title,
                selectedColor = uiState.brushColor,
                colorList = BrushColorList,
                onColorSelect = { color: Color ->
                    mainViewModel.updateSetting(BrushColor(color))
                },
                onDismissRequest = { showBrushColorDialog = false },
            )
        }

        if (showTeamDialog) {
            //get the center of the screen
            val screenWidth = getScreenWidthInDp().toPx()
            val screenHeight = getScreenHeightInDp().toPx()

            TeamSelectionDialog(onTeamSelected = { team ->
                val teamSize = uiState.player.filter { it.team == team }.size
                val playerIndex = teamSize + 1
                if (playerIndex <= MAX_PLAYER_INDEX) {
                    val playerInfo = PlayerInfo(
                        id = null,
                        team = team,
                        index = playerIndex,
                        name = "Player $playerIndex",
                        offset = Offset(
                            (screenWidth / 2), (screenHeight / 2)
                        ),
                    )
                    mainViewModel.updateSetting(PlayerInsert(playerInfo))
                }
                showTeamDialog = false
            }, onDismissRequest = {
                showTeamDialog = false
            })
        }

        if (showPlayerDialog) {
            val playerInfo =
                mainViewModel.getPlayerInfo(selectedPlayerId ?: return@ConstraintLayout)
                    ?: return@ConstraintLayout
            PlayerSettingDialog(playerInfo = playerInfo,
                onPlayerInfoChange = { updatedPlayerInfo ->
                    mainViewModel.updateSetting(
                        PlayerUpdate(updatedPlayerInfo)
                    )
                    showPlayerDialog = false
                },
                onDismissRequest = {
                    showPlayerDialog = false
                })
        }

    }

}