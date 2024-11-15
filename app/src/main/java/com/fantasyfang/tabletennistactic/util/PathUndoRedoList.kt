package com.fantasyfang.tabletennistactic.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Path
import com.fantasyfang.tabletennistactic.repository.path.PathProperties

class PathUndoRedoList {
    private val redoStack: SnapshotStateList<Pair<Path, PathProperties>> = mutableStateListOf()
    private val currentList: SnapshotStateList<Pair<Path, PathProperties>> = mutableStateListOf()

    fun add(element: Pair<Path, PathProperties>) {
        currentList.add(element)
    }

    fun getPathList(): List<Pair<Path, PathProperties>> {
        return currentList
    }

    fun undo() {
        if (currentList.isNotEmpty()) {
            val currentId = currentList.last().first.hashCode()
            val linesToRemove = currentList.filter { it.first.hashCode() == currentId }
            redoStack.addAll(linesToRemove)
            currentList.apply { removeIf { it.first.hashCode() == currentId } }
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

    fun clear() {
        currentList.clear()
        redoStack.clear()
    }
}