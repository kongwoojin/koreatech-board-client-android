package com.kongjak.koreatechboard.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.board.BoardMain
import com.kongjak.koreatechboard.ui.board.Drawer
import com.kongjak.koreatechboard.ui.routes.Department
import com.kongjak.koreatechboard.ui.routes.deptList
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoreatechBoardTheme {
                AppMainScreen()
            }
        }
    }

    @Composable
    fun TopBar(onButtonClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name)
                )
            },
            navigationIcon = {
                IconButton(onClick = { onButtonClicked() }) {
                    Icon(Icons.Default.Menu, contentDescription = "")
                }
            },
            backgroundColor = MaterialTheme.colors.primary
        )
    }

    @Preview
    @Composable
    fun AppMainScreen() {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colors.background) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            ModalDrawer(
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    Drawer(
                        onDestinationClicked = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Department.School.dept.name
                ) {
                    deptList.forEach { item ->
                        composable(item.dept.name) {
                            BoardMain(
                                openDrawer = {
                                    openDrawer()
                                }, site = item.dept
                            )
                        }
                    }
                }
            }
        }
    }
}
