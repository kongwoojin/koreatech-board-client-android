package com.kongjak.koreatechboard.ui.main

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kongjak.koreatechboard.BuildConfig
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.BottomNav
import com.kongjak.koreatechboard.ui.NavigationGraph
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBar
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBarAction
import com.kongjak.koreatechboard.ui.permission.CheckNotificationPermission
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.util.routes.MainRoute
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.BUILD_TYPE == "debug") {
            getFirebaseToken(this)
        }

        setContent {
            var startDestination by remember { mutableStateOf(MainRoute.Home.name) }

            val defaultScreen = intent.getStringExtra("screen")

            if (defaultScreen != null) {
                startDestination = MainRoute.valueOf(defaultScreen).name
            }

            mainViewModel.collectSideEffect { mainViewModel.handleSideEffect(it) }

            val uiState by mainViewModel.collectAsState()
            val isDynamicColor = uiState.isDynamicTheme
            val isDarkTheme = uiState.isDarkTheme ?: isSystemInDarkTheme()

            KoreatechBoardTheme(
                dynamicColor = isDynamicColor,
                darkTheme = isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        CheckNotificationPermission()
                    }
                    MainScreen(
                        startDestination = startDestination,
                        externalLink = uiState.externalLink,
                        setExternalLink = { url ->
                            mainViewModel.setExternalLink(url)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    startDestination: String = MainRoute.Home.name,
    externalLink: String?,
    setExternalLink: (String) -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val mainScreenRoutes = listOf(
        MainRoute.Home.name,
        MainRoute.Board.name,
        MainRoute.Settings.name
    )

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route?.split("/")?.get(0)

    val canGoBack = if (currentRoute == null) {
        false
    } else {
        !(mainScreenRoutes.contains(currentRoute) || currentRoute == startDestination)
    }

    Scaffold(
        topBar = {
            val actionList = listOf(
                KoreatechBoardAppBarAction(
                    icon = Icons.Default.Notifications,
                    action = {
                        navController.navigate(MainRoute.Notice.name)
                    },
                    contentDescription = stringResource(id = R.string.content_description_notification)
                )
            )

            val externalLinkAction = listOf(
                KoreatechBoardAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.ic_open_in_browser),
                    action = {
                        if (externalLink != null) {
                            val builder = CustomTabsIntent.Builder()
                            val customTabsIntent = builder.build()
                            customTabsIntent.launchUrl(context, Uri.parse(externalLink))
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.open_in_browser_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    contentDescription = stringResource(
                        id = R.string.content_description_open_in_browser
                    )
                )
            )

            KoreatechBoardAppBar(
                canGoBack = canGoBack,
                backAction = {
                    navController.navigateUp()
                },
                actionList = if (currentRoute == MainRoute.Article.name) externalLinkAction else actionList
            )
        },
        bottomBar = {
            BottomNav(navController = navController)
        },
        content = { contentPadding ->
            NavigationGraph(
                modifier = Modifier.padding(contentPadding),
                navController = navController,
                currentRoute = startDestination,
                setExternalLink = setExternalLink
            )
        }
    )
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
