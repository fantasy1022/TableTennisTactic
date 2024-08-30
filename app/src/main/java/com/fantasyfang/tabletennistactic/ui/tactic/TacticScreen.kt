package com.fantasyfang.tabletennistactic.ui.tactic

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.fantasyfang.tabletennistactic.ui.component.FloorView
import com.fantasyfang.tabletennistactic.util.DrawMode
import androidx.constraintlayout.compose.ConstraintLayout
import com.fantasyfang.tabletennistactic.ui.component.TennisTableView

@Composable
fun TacticScreen(
    navController: NavController,
) {
    val drawMode by remember { mutableStateOf(DrawMode.Touch) }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ){
        val (floorView) = createRefs()

        val floorViewModifier = Modifier.constrainAs(floorView) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }

        FloorView(
            color = Color.Red,
        )
        TennisTableView(
            backgroundColor = Color.Blue
        )
    }


}