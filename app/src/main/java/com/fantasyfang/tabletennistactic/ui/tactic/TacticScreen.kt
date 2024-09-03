package com.fantasyfang.tabletennistactic.ui.tactic

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.fantasyfang.tabletennistactic.ui.component.FloorView
import com.fantasyfang.tabletennistactic.ui.component.SettingBarView
import com.fantasyfang.tabletennistactic.ui.component.TennisTableView

@Composable
fun TacticScreen(
    navController: NavController,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (bottomIconRow) = createRefs()

        val settingIconViewModifier = Modifier
            .navigationBarsPadding()
            .constrainAs(bottomIconRow) {
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

        FloorView(
            color = Color.Red,
        )
        TennisTableView(
            backgroundColor = Color.Blue
        )
        SettingBarView(settingIconViewModifier, color = Color.Red)
    }


}