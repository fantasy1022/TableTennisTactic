package com.fantasyfang.tabletennistactic.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(private val coroutineScope: CoroutineScope) {
    private var job: Job? = null

    fun debounce(waitMs: Long, action: () -> Unit) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(waitMs)
            action()
        }
    }
}