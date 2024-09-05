package com.fantasyfang.tabletennistactic.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.data.player.Team

@Composable
fun TeamSelectionDialog(
    onTeamSelected: (Team) -> Unit, onDismissRequest: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.team_title)) },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onTeamSelected(Team.TEAM_1) }) {
                    Text("Team1")
                }
                Button(onClick = { onTeamSelected(Team.TEAM_2) }) {
                    Text("Team2")
                }
            }
        },
        confirmButton = { },
        dismissButton = { })
}