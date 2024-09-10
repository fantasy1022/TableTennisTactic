package com.fantasyfang.tabletennistactic.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo

@Composable
fun PlayerSettingDialog(
    playerInfo: PlayerInfo,
    onPlayerInfoChange: (PlayerInfo) -> Unit,
    onDismissRequest: () -> Unit
) {
    var playerName by remember { mutableStateOf(playerInfo.name) }
    var playerIndex by remember { mutableStateOf(playerInfo.index.toString()) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.player_settings_title)) },
        text = {
            Column {
                TextField(
                    value = playerName,
                    onValueChange = { playerName = it },
                    label = { Text(text = stringResource(id = R.string.player_settings_name)) }
                )
                TextField(
                    value = playerIndex,
                    onValueChange = { playerIndex = it },
                    label = { Text(text = stringResource(id = R.string.player_settings_index)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedPlayerInfo = playerInfo.copy(
                    name = playerName,
                    index = playerIndex.toIntOrNull() ?: playerInfo.index
                )
                onPlayerInfoChange(updatedPlayerInfo)
                onDismissRequest()
            }) {
                Text(text = stringResource(id = R.string.common_ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.common_cancel))
            }
        }
    )
}