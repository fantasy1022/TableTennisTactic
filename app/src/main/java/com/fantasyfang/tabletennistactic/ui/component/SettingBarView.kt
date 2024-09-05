package com.fantasyfang.tabletennistactic.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.ui.tactic.TacticUiState
import com.fantasyfang.tabletennistactic.util.DrawMode

@Composable
fun SettingBarView(
    modifier: Modifier,
    uiState: TacticUiState,
    drawMode: DrawMode,
    onPathUndoClick: () -> Unit,
    onPathRedoClick: () -> Unit,
    onEditIconClick: () -> Unit,
    onEraseIconClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val drawModeTransition = updateTransition(targetState = drawMode, label = "DrawModeTransition")

    Row(
        modifier = modifier
            .background(color = uiState.floorColor)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
    ) {
        SettingIconView()
        AddPlayerIconView()
        AnimateIcon(drawModeTransition) {
            BrushColorIconView(uiState.brushColor)
        }
        AnimateIcon(drawModeTransition) {
            BrushWidthIconView(
                uiState.brushWidth, uiState.brushColor
            )
        }
        EditIconView(drawMode, onEditIconClick)
        EraseIconView(drawMode, onEraseIconClick)
        UndoIconView(onPathUndoClick)
        RedoIconView(onPathRedoClick)
        ClearAllIconView()
    }
}


@Composable
private fun ClearAllIconView() {
    IconButton(onClick = {}) {
        Icon(
            painterResource(id = R.drawable.ic_clear_all_24),
            contentDescription = "Clear",
            tint = Color.White
        )
    }
}

@Composable
private fun RedoIconView(onPathRedo: () -> Unit) {
    IconButton(onClick = {
        onPathRedo.invoke()
    }) {
        Icon(
            painterResource(id = R.drawable.ic_redo_24),
            contentDescription = "Redo",
            tint = Color.White
        )
    }
}

@Composable
private fun UndoIconView(onPathUndo: () -> Unit) {
    IconButton(onClick = {
        onPathUndo.invoke()
    }) {
        Icon(
            painterResource(id = R.drawable.ic_undo_24),
            contentDescription = "Undo",
            tint = Color.White
        )
    }
}

@Composable
private fun SettingIconView() {
    IconButton(onClick = {}) {
        Icon(
            Icons.Default.Settings, contentDescription = "Settings", tint = Color.White
        )
    }
}

@Composable
private fun AddPlayerIconView() {
    IconButton(onClick = {}) {
        Icon(
            painterResource(id = R.drawable.ic_person_add_24),
            contentDescription = "Add Person",
            tint = Color.White
        )
    }
}

@Composable
private fun EditIconView(drawMode: DrawMode, onEditIconClick: () -> Unit) {
    IconButton(onClick = {
        onEditIconClick.invoke()
//        onDrawModeChange(if (drawMode != DrawMode.Draw) DrawMode.Draw else DrawMode.Touch)
    }) {
        Icon(
            Icons.Default.Edit,
            contentDescription = "Edit",
            tint = if (drawMode == DrawMode.Draw) Color.Cyan else Color.White
        )
    }
}

@Composable
private fun EraseIconView(drawMode: DrawMode, onEraseIconClick: () -> Unit) {
    IconButton(onClick = {
        onEraseIconClick.invoke()
//        onDrawModeChange(if (drawMode != DrawMode.Erase) DrawMode.Erase else DrawMode.Touch)
    }) {
        Icon(
            painterResource(id = R.drawable.ic_eraser_24),
            contentDescription = "Eraser",
            tint = if (drawMode == DrawMode.Erase) Color.Yellow else Color.White
        )
    }
}

@Composable
private fun BrushWidthIconView(brushWidth: Float, brushColor: Color) {
    IconButton(onClick = {

    }) {
        Box(
            modifier = Modifier
                .size(25.dp)
                .background(Color.White, RoundedCornerShape(10))
        ) {
            Box(
                modifier = Modifier
                    .width(brushWidth.dp)
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .background(brushColor)
            )
        }
    }
}

@Composable
private fun BrushColorIconView(brushColor: Color) {
    IconButton(onClick = {

    }) {
        Box(
            modifier = Modifier
                .size(25.dp)
                .border(2.dp, Color.White)
                .background(brushColor, RoundedCornerShape(10))
        )
    }
}

@Composable
private fun AnimateIcon(transition: Transition<DrawMode>, view: @Composable() () -> Unit) {
    transition.AnimatedContent(
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        },
    ) { targetState ->
        if (targetState == DrawMode.Draw) {
            view.invoke()
        }
    }
}