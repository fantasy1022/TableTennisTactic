package com.fantasyfang.tabletennistactic.ui.component.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ColorSelectDialog(
    @StringRes titleId: Int,
    selectedColor: Color,
    colorList: List<Color>,
    onColorSelect: (Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    AlertDialog(onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = titleId)) },
        text = {
            Column {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4)
                ) {
                    items(colorList.size) { index ->
                        Box(
                            Modifier
                                .padding(top = 5.dp, bottom = 5.dp)
                                .border(
                                    width = if (colorList[index] == selectedColor) 2.dp else 0.dp,
                                    color = if (colorList[index] == selectedColor) selectedColor else Color.Transparent,
                                    shape = CircleShape,
                                )
                        ) {
                            IconButton(modifier = Modifier
                                .clip(CircleShape)
                                .align(Alignment.Center)
                                .background(colorList[index]),
                                onClick = {
                                    isClicked = !isClicked
                                    onColorSelect(colorList[index])
                                    onDismissRequest()
                                }) {
                            }
                        }
                    }
                }
            }
        },
        confirmButton = { })

}