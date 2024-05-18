package com.kongjak.koreatechboard.ui.main

import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.KoreatechBoardNavigationBar
import com.kongjak.koreatechboard.ui.KoreatechBoardNavigationRail
import com.kongjak.koreatechboard.ui.NavigationGraph
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBar
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBarAction
import com.kongjak.koreatechboard.util.routes.MainRoute

@Composable
fun MainScreen(
    startDestination: String = MainRoute.Home.name,
    isDarkTheme: Boolean,
    isLargeScreen: Boolean,
    initDepartment: Int,
    userDepartment: Int,
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
                actionList = when (currentRoute) {
                    MainRoute.Notice.name -> emptyList()
                    MainRoute.Article.name -> externalLinkAction
                    else -> actionList
                }
            )
        },
        bottomBar = {
            if (!isLargeScreen) {
                KoreatechBoardNavigationBar(navController = navController)
            }
        },
        content = { contentPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                if (isLargeScreen) {
                    KoreatechBoardNavigationRail(
                        navController = navController
                    )
                }
                NavigationGraph(
                    navController = navController,
                    currentRoute = startDestination,
                    isDarkTheme = isDarkTheme,
                    initDepartment = initDepartment,
                    userDepartment = userDepartment,
                    setExternalLink = setExternalLink
                )
            }
        }
    )
}
