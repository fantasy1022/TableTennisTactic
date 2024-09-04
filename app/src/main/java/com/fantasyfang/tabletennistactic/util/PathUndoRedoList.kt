package com.fantasyfang.tabletennistactic.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Path
import com.fantasyfang.tabletennistactic.repository.PathProperties

class PathUndoRedoList {
    private val redoStack: SnapshotStateList<Pair<Path, PathProperties>> = mutableStateListOf()
    var currentList: SnapshotStateList<Pair<Path, PathProperties>> = mutableStateListOf()

    fun add(element: Pair<Path, PathProperties>) {
        currentList.add(element)
    }

    fun undo() {
        if (currentList.isNotEmpty()) {
            val currentId = currentList.last().first.hashCode()
            val linesToRemove = currentList.filter { it.first.hashCode() == currentId }
            redoStack.addAll(linesToRemove)
            currentList = currentList.apply { removeIf { it.first.hashCode() == currentId } }
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val currentId = redoStack.last().first.hashCode()
            val linesToAdd = redoStack.filter { it.first.hashCode() == currentId }
            redoStack.removeAll(linesToAdd)
            currentList.addAll(linesToAdd)
        }
    }
}