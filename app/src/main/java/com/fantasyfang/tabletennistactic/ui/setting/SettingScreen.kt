package com.fantasyfang.tabletennistactic.ui.setting

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.extension.SetStatusSystemBarColor

import com.fantasyfang.tabletennistactic.ui.component.dialog.ColorSelectDialog
import com.fantasyfang.tabletennistactic.ui.theme.FloorColorList
import com.fantasyfang.tabletennistactic.ui.theme.TableColorList
import com.fantasyfang.tabletennistactic.ui.theme.TeamColorList
import com.fantasyfang.tabletennistactic.usecase.setting.SetSettingsUseCase
import com.fantasyfang.tabletennistactic.util.Const.Companion.PLAYER_ICON_RADIUS_INTERVAL
import com.fantasyfang.tabletennistactic.util.Const.Companion.PLAYER_ICON_RADIUS_MIN

import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun SettingScreen(
    colorScheme: ColorScheme,
    settingViewModel: SettingViewModel = koinViewModel()
) {

    val uiState = settingViewModel.uiState.collectAsState().value
    SetStatusSystemBarColor(colorScheme.background)

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.setting_title)) },
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GeneralSettings(uiState.isPreventSleep) { isPreventSleep ->
                    settingViewModel.saveSetting(
                        SetSettingsUseCase.SettingType.IsPreventSleep(
                            isPreventSleep
                        )
                    )
                }
            }
            item {
                TableSettings(uiState.tableColor, uiState.floorColor, onCourtColorChange = {
                    settingViewModel.saveSetting(
                        SetSettingsUseCase.SettingType.CourtColor(it)
                    )
                }, onBorderColorChange = {
                    settingViewModel.saveSetting(
                        SetSettingsUseCase.SettingType.BorderColor(it)
                    )
                })
            }
            item {
                Log.d("Fam", "TeamSettings: ${uiState.team1Color} ${uiState.team2Color}")
                TeamSettings(team1Color = uiState.team1Color,
                    team2Color = uiState.team2Color,
                    onTeam1ColorChange = {
                        settingViewModel.saveSetting(
                            SetSettingsUseCase.SettingType.Team1Color(it)
                        )
                    },
                    onTeam2ColorChange = {
                        settingViewModel.saveSetting(
                            SetSettingsUseCase.SettingType.Team2Color(it)
                        )
                    })
            }
            item {
                PlayerSettings(playerIconRadius = uiState.playerIconRadius,
                    isShowPlayerName = uiState.isShowPlayerName,
                    onSliderPositionChange = {
                        settingViewModel.saveSetting(
                            SetSettingsUseCase.SettingType.PlayerIconRadius(it)
                        )
                    },
                    onIsPlayerNameChange = {
                        settingViewModel.saveSetting(
                            SetSettingsUseCase.SettingType.IsShowPlayerName(it)
                        )
                    })
            }
        }
    }
}

@Composable
fun GeneralSettings(
    isPreventSleep: Boolean, onPreventSleepChange: (Boolean) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.general_title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {}) {
            Row( //TODO: extract to a composable function
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = stringResource(id = R.string.prevent_sleep_title),
                    tint = Color(0xFF6A994E)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Prevent sleep")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = isPreventSleep, onCheckedChange = {
                    onPreventSleepChange(it)
                })
            }
        }
    }
}

@Composable
fun TableSettings(
    tableColor: Color, floorColor: Color,
    onCourtColorChange: (Color) -> Unit,
    onBorderColorChange: (Color) -> Unit,
) {
    var isShowTableColorDialog by remember { mutableStateOf(false) }
    var isShowFloorColorDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = stringResource(id = R.string.court_title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isShowTableColorDialog = true
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                ColorBox(color = tableColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(id = R.string.court_color_title))
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isShowFloorColorDialog = true
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                ColorBox(color = floorColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(id = R.string.border_color_title))
            }
        }
    }

    if (isShowTableColorDialog) {
        ColorSelectDialog(titleId = R.string.table_color_title,
            selectedColor = tableColor,
            colorList = TableColorList,
            onColorSelect = {
                onCourtColorChange(it)
            },
            onDismissRequest = { isShowTableColorDialog = false })
    }

    if (isShowFloorColorDialog) {
        ColorSelectDialog(titleId = R.string.floor_color_title,
            selectedColor = floorColor,
            colorList = FloorColorList,
            onColorSelect = {
                onBorderColorChange(it)
            },
            onDismissRequest = { isShowFloorColorDialog = false })
    }
}

@Composable
fun TeamSettings(
    team1Color: Color,
    team2Color: Color,
    onTeam1ColorChange: (Color) -> Unit,
    onTeam2ColorChange: (Color) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.team_title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        TeamRow(
            titleId = R.string.team_1_color_title,
            selectedColor = team1Color,
            colorOptionList = TeamColorList,
            onColorChange = onTeam1ColorChange
        )
        TeamRow(
            titleId = R.string.team_2_color_title,
            selectedColor = team2Color,
            colorOptionList = TeamColorList,
            onColorChange = onTeam2ColorChange
        )
    }
}


@Composable
fun PlayerSettings(
    playerIconRadius: Dp,
    isShowPlayerName: Boolean,
    onSliderPositionChange: (Dp) -> Unit,
    onIsPlayerNameChange: (Boolean) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.player_title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            var sliderPositions by remember { mutableStateOf((playerIconRadius - PLAYER_ICON_RADIUS_MIN) / PLAYER_ICON_RADIUS_INTERVAL) }

            LaunchedEffect(playerIconRadius) { //TODO:Check the snapshotFlow
                sliderPositions =
                    (playerIconRadius - PLAYER_ICON_RADIUS_MIN) / PLAYER_ICON_RADIUS_INTERVAL
                snapshotFlow { sliderPositions }
            }
            Column {
                Slider(value = sliderPositions, onValueChange = {
                    sliderPositions = it
                }, onValueChangeFinished = {
                    onSliderPositionChange((sliderPositions * PLAYER_ICON_RADIUS_INTERVAL.value + PLAYER_ICON_RADIUS_MIN.value).dp)
                })
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size((((sliderPositions * PLAYER_ICON_RADIUS_INTERVAL.value + PLAYER_ICON_RADIUS_MIN.value)) * 2).dp)
                        .background(Color(0xFF6A994E), shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                AnimatedVisibility(
                    visible = isShowPlayerName,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Player1",
                    )
                }

                Row( //TODO: extract to a composable function
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.show_player_name_title),
                        tint = Color(0xFF6A994E)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(id = R.string.show_player_name_title))
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(checked = isShowPlayerName, onCheckedChange = {
                        onIsPlayerNameChange(it)
                    })
                }

            }
        }
    }
}


@Composable
fun TeamRow(
    @StringRes titleId: Int,
    selectedColor: Color,
    colorOptionList: List<Color>,
    onColorChange: (Color) -> Unit
) {
    var isShowColorDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            isShowColorDialog = true
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            ColorBox(color = selectedColor)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = titleId))
        }
    }

    if (isShowColorDialog) {
        ColorSelectDialog(titleId = titleId,
            selectedColor = selectedColor,
            colorList = colorOptionList,
            onColorSelect = {
                onColorChange(it)
            },
            onDismissRequest = { isShowColorDialog = false })
    }

}


@Composable
fun ColorBox(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(color, shape = CircleShape)
    )
}