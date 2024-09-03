package com.fantasyfang.tabletennistactic.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.util.DrawMode


@Composable
fun SettingBarView(modifier: Modifier, color: Color) {
    val drawMode by remember { mutableStateOf(DrawMode.Touch) }

    Row(
        modifier = modifier.background(color = color),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
    ) {
        SettingIconView()
        AddPlayerIconView()
        EditIconView(drawMode)
        EraseIconView(drawMode)
        UndoIconView()
        RedoIconView()
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
private fun RedoIconView() {
    IconButton(onClick = {}) {
        Icon(
            painterResource(id = R.drawable.ic_redo_24),
            contentDescription = "Redo",
            tint = Color.White
        )
    }
}

@Composable
private fun UndoIconView() {
    IconButton(onClick = {}) {
        Icon(
            painterResource(id = R.drawable.ic_undo_24),
            contentDescription = "Undo",
            tint = Color.White
        )
    }
}

@Composable
private fun EraseIconView(drawMode: DrawMode) {
    IconButton(onClick = {}) {
        Icon(
            painterResource(id = R.drawable.ic_eraser_24),
            contentDescription = "Eraser",
            tint = if (drawMode == DrawMode.Erase) Color.Yellow else Color.White
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
private fun EditIconView(drawMode: DrawMode) {
    IconButton(onClick = {}) {
        Icon(
            Icons.Default.Edit,
            contentDescription = "Edit",
            tint = if (drawMode == DrawMode.Draw) Color.Cyan else Color.White
        )
    }
}