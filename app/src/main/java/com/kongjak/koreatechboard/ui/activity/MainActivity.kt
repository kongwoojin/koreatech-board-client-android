package com.kongjak.koreatechboard.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.ui.board.BoardScreen
import com.kongjak.koreatechboard.ui.home.HomeScreen
import com.kongjak.koreatechboard.ui.main.MainViewModel
import com.kongjak.koreatechboard.ui.settings.SettingsScreen
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDynamicColor by mainViewModel.isDynamicTheme.observeAsState(true)
            KoreatechBoardTheme(
                dynamicColor = isDynamicColor
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            AppBar()
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        content = { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.app_name))
    })
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Board,
        BottomNavigationItem.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                label = {
                    Text(text = stringResource(id = item.stringResource))
                },

                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.name
                    )
                },

                selected = currentRoute == item.name,

                alwaysShowLabel = true,

                onClick = {
                    navController.navigate(item.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                // Control all the colors of the icon
                colors = NavigationBarItemDefaults.colors()
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavigationItem.Home.name) {
        composable(BottomNavigationItem.Home.name) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Board.name) {
            BoardScreen()
        }
        composable(BottomNavigationItem.Settings.name) {
            SettingsScreen()
        }
    }
}
