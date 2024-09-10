package com.fantasyfang.tabletennistactic.extension

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat


@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current
    return with(density) { this@toPx.toPx() }
}

@Composable
fun getScreenWidthInDp(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
}

@Composable
fun getScreenHeightInDp(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
}

@Composable
fun SetStatusSystemBarColor(color: Color) {
    val window = (LocalContext.current as Activity).window
    val insetsController = WindowCompat.getInsetsController(window, window.decorView)
    SideEffect {
        window.statusBarColor = color.toArgb()
        window.navigationBarColor = color.toArgb()
        insetsController.isAppearanceLightStatusBars = color.luminance() > 0.5
        insetsController.isAppearanceLightNavigationBars = color.luminance() > 0.5
    }
}