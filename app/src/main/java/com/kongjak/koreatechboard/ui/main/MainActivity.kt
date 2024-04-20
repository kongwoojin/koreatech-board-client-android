package com.kongjak.koreatechboard.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kongjak.koreatechboard.BuildConfig
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBar
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBarAction
import com.kongjak.koreatechboard.ui.main.board.BoardScreen
import com.kongjak.koreatechboard.ui.main.home.HomeScreen
import com.kongjak.koreatechboard.ui.main.settings.SettingsScreen
import com.kongjak.koreatechboard.ui.notice.NoticeActivity
import com.kongjak.koreatechboard.ui.permission.CheckNotificationPermission
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defaultScreen = intent.getStringExtra("screen")
        if (defaultScreen != null) {
            mainViewModel.setDefaultScreen(BottomNavigationItem.valueOf(defaultScreen))
        }

        if (BuildConfig.BUILD_TYPE == "debug") {
            getFirebaseToken(this)
        }

        setContent {
            mainViewModel.collectSideEffect { mainViewModel.handleSideEffect(it) }

            val isDynamicColor by themeViewModel.isDynamicTheme.observeAsState(true)
            val isDarkTheme by themeViewModel.isDarkTheme.observeAsState()
            KoreatechBoardTheme(
                dynamicColor = isDynamicColor,
                darkTheme = isDarkTheme ?: isSystemInDarkTheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        CheckNotificationPermission()
                    }
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            val actionList = listOf(
                KoreatechBoardAppBarAction(
                    icon = Icons.Default.Notifications,
                    action = {
                        val intent = Intent(context, NoticeActivity::class.java)
                        context.startActivity(intent)
                    },
                    contentDescription = stringResource(id = R.string.content_description_notification)
                )
            )
            KoreatechBoardAppBar(
                actionList = actionList
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        content = { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                NavigationGraph(
                    navController = navController,
                    mainViewModel = mainViewModel
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = {
                val intent = Intent(context, NoticeActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(id = R.string.content_description_notification)
                )
            }
        }
    )
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
                        contentDescription = stringResource(id = item.stringResource)
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
fun NavigationGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    val uiState by mainViewModel.collectAsState()
    val defaultScreen = uiState.defaultScreen
    val initDepartment = uiState.initDepartment
    val userDepartment = uiState.userDepartment

    NavHost(navController = navController, startDestination = defaultScreen.name) {
        composable(BottomNavigationItem.Home.name) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Board.name) {
            BoardScreen(
                initDepartment = initDepartment,
                userDepartment = userDepartment,
            )
        }
        composable(BottomNavigationItem.Settings.name) {
            SettingsScreen()
        }
    }
}

fun getFirebaseToken(context: Context) {
    val tag = "FCM"
    FirebaseMessaging.getInstance().token.addOnCompleteListener(
        OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            val msg = context.getString(R.string.firebase_token, token)
            Log.d(tag, msg)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    )
}
