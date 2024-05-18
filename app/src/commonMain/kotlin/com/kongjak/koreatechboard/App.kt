package com.kongjak.koreatechboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kongjak.koreatechboard.ui.main.MainScreen
import com.kongjak.koreatechboard.ui.main.MainViewModel
import com.kongjak.koreatechboard.ui.settings.darkTheme
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.util.routes.MainRoute
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App() {
    val windowSizeClass = calculateWindowSizeClass()
    val isLargeScreen = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact
    val startDestination by remember { mutableStateOf(MainRoute.Home.name) }

    val mainViewModel: MainViewModel = koinViewModel()
    mainViewModel.collectSideEffect { mainViewModel.handleSideEffect(it) }

    val uiState by mainViewModel.collectAsState()
    val isDynamicColor = uiState.isDynamicTheme
    val isDarkTheme = uiState.isDarkTheme ?: isSystemInDarkTheme()
    val initDestination = uiState.initDepartment
    val userDestination = uiState.userDepartment

    KoreatechBoardTheme(
        darkTheme = isDarkTheme,
        dynamicColor = isDynamicColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen(
                startDestination = startDestination,
                isDarkTheme = isDarkTheme,
                isLargeScreen = isLargeScreen,
                initDepartment = initDestination,
                userDepartment = userDestination,
                externalLink = uiState.externalLink,
                setExternalLink = { url ->
                    mainViewModel.setExternalLink(url)
                }
            )
        }
    }
}