package com.fantasyfang.tabletennistactic.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.positionChange
import com.fantasyfang.tabletennistactic.repository.PathProperties
import com.fantasyfang.tabletennistactic.ui.tactic.TacticUiState
import com.fantasyfang.tabletennistactic.util.DrawMode
import com.fantasyfang.tabletennistactic.util.MotionEvent
import com.fantasyfang.tabletennistactic.util.PathUndoRedoList

@Composable
fun DrawingView(modifier: Modifier, uiState: TacticUiState, drawMode: DrawMode) {
    var motionEvent by remember { mutableStateOf(MotionEvent.Idle) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentPathProperty by remember { mutableStateOf(PathProperties()) }
    val paths by remember { mutableStateOf(PathUndoRedoList()) } //TODO: Use savedPath

    Column(
        modifier = modifier
    ) {
        val drawModifier =
            if (drawMode == DrawMode.Draw || drawMode == DrawMode.Erase) {
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .dragMotionEvent(onDragStart = { pointerInputChange ->
                        motionEvent = MotionEvent.Down
                        currentPosition = pointerInputChange.position

                        if (pointerInputChange.pressed != pointerInputChange.previousPressed) {
                            pointerInputChange.consume()
                        }
                    }, onDrag = { pointerInputChange ->
                        motionEvent = MotionEvent.Move
                        currentPosition = pointerInputChange.position

                        if (pointerInputChange.positionChange() != Offset.Zero) {
                            pointerInputChange.consume()
                        }
                    }, onDragEnd = { pointerInputChange ->
                        motionEvent = MotionEvent.Up

                        if (pointerInputChange.pressed != pointerInputChange.previousPressed) {
                            pointerInputChange.consume()
                        }
                    })
            } else {
                modifier
            }

        Canvas(modifier = drawModifier) {
            // Record the path and properties when MotionEvent.Up is triggered
            when (motionEvent) {
                MotionEvent.Down -> {
                    if (drawMode != DrawMode.Touch) {
                        currentPath.moveTo(currentPosition.x, currentPosition.y)
                    }
                    previousPosition = currentPosition
                }

                MotionEvent.Move -> {
                    if (drawMode != DrawMode.Touch) {
                        currentPath.quadraticBezierTo(
                            previousPosition.x,
                            previousPosition.y,
                            (previousPosition.x + currentPosition.x) / 2,
                            (previousPosition.y + currentPosition.y) / 2
                        )
                    }

                    previousPosition = currentPosition
                }

                MotionEvent.Up -> {
                    if (drawMode != DrawMode.Touch) {
                        currentPath.lineTo(currentPosition.x, currentPosition.y)
                        paths.add(Pair(currentPath, currentPathProperty))

                        // Since paths are keys for map, use new one for each key
                        // and have separate path for each down-move-up gesture cycle
                        currentPath = Path()

                        // Create new instance of path properties to have new path and properties
                        // only for the one currently being drawn
                        currentPathProperty = PathProperties(
                            strokeWidth = uiState.brushWidth,
                            color = uiState.brushColor,
                            eraseMode = currentPathProperty.eraseMode
                        )
                    }

                    // If we leave this state at MotionEvent.Up it causes current path to draw
                    // line from (0,0) if this composable recomposes when draw mode is changed
                    currentPosition = Offset.Unspecified
                    previousPosition = currentPosition
                    motionEvent = MotionEvent.Idle
                }

                else -> Unit
            }

            // Draw the paths
            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)
                paths.currentList.forEach {
                    val path = it.first
                    val property = it.second

                    if (!property.eraseMode) {
                        drawPath(
                            color = property.color, path = path, style = Stroke(
                                width = property.strokeWidth,
                            )
                        )
                    } else {
                        drawPath(
                            color = Color.Transparent, path = path, style = Stroke(
                                width = currentPathProperty.strokeWidth,
                            ), blendMode = BlendMode.Clear
                        )
                    }
                }

                if (motionEvent != MotionEvent.Idle) {
                    if (!currentPathProperty.eraseMode) {
                        drawPath(
                            color = uiState.brushColor,
                            path = currentPath,
                            style = Stroke(
                                width = currentPathProperty.strokeWidth,
                            )
                        )
                    } else {
                        drawPath(
                            color = Color.Transparent, path = currentPath, style = Stroke(
                                width = currentPathProperty.strokeWidth,
                            ), blendMode = BlendMode.Clear
                        )
                    }
                }
                restoreToCount(checkPoint)
            }

        }
    }
}