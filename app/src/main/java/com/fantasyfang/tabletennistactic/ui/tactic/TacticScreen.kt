package com.fantasyfang.tabletennistactic.ui.tactic

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.ui.component.DrawingView
import com.fantasyfang.tabletennistactic.ui.component.FloorView
import com.fantasyfang.tabletennistactic.ui.component.SettingBarView
import com.fantasyfang.tabletennistactic.ui.component.TennisTableView
import com.fantasyfang.tabletennistactic.ui.component.dialog.BrushWidthDialog
import com.fantasyfang.tabletennistactic.ui.component.dialog.ColorSelectDialog
import com.fantasyfang.tabletennistactic.ui.theme.BrushColorList
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.BrushColor
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.BrushWidth
import com.fantasyfang.tabletennistactic.util.DrawMode
import com.fantasyfang.tabletennistactic.util.PathUndoRedoList

@Composable
fun TacticScreen(
    navController: NavController,
    mainViewModel: TacticViewModel,
) {
    val uiState = mainViewModel.uiState.collectAsState().value
    var drawMode by remember { mutableStateOf(DrawMode.Touch) }
    val paths by remember { mutableStateOf(PathUndoRedoList()) } //TODO: Use savedPath
    var showBrushWidthDialog by remember { mutableStateOf(false) }
    var showBrushColorDialog by remember { mutableStateOf(false) }

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

        SettingBarView(
            settingIconViewModifier,
            uiState = uiState,
            drawMode = drawMode,
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
            }
        )

        if (showBrushWidthDialog) {
            BrushWidthDialog(
                brushSize = uiState.brushWidth,
                onBrushSizeChange = { width ->
                    //TODO: Save brush width
                    mainViewModel.saveSetting(BrushWidth(width))
                },
                onDismissRequest = { showBrushWidthDialog = false })
        }

        if (showBrushColorDialog) {
            ColorSelectDialog(
                titleId = R.string.brush_color_title,
                selectedColor = uiState.brushColor,
                colorList = BrushColorList,
                onColorSelect = { color: Color ->
                    mainViewModel.saveSetting(BrushColor(color))
                },
                onDismissRequest = { showBrushColorDialog = false },
            )
        }

    }

}