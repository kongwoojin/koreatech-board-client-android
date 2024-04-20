package com.kongjak.koreatechboard.ui.article

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBar
import com.kongjak.koreatechboard.ui.components.KoreatechBoardAppBarAction
import com.kongjak.koreatechboard.ui.network.NetworkViewModel
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.ui.viewmodel.ThemeViewModel
import com.kongjak.koreatechboard.util.findActivity
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.UUID

@AndroidEntryPoint
class ArticleActivity : ComponentActivity() {

    private val articleViewModel: ArticleViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = UUID.fromString(intent.getStringExtra("uuid"))
        val department = intent.getStringExtra("department")!!

        setContent {
            ArticleMain(
                articleViewModel = articleViewModel,
                themeViewModel = themeViewModel,
                networkViewModel = networkViewModel,
                department = department,
                uuid = uuid
            )
        }
    }
}

@Composable
fun ArticleMain(
    articleViewModel: ArticleViewModel,
    themeViewModel: ThemeViewModel,
    networkViewModel: NetworkViewModel,
    department: String,
    uuid: UUID
) {
    val context = LocalContext.current
    val uiState by articleViewModel.collectAsState()
    val articleUrl = uiState.url
    val isDynamicColor by themeViewModel.isDynamicTheme.observeAsState(true)
    val isDarkTheme by themeViewModel.isDarkTheme.observeAsState()

    KoreatechBoardTheme(
        dynamicColor = isDynamicColor,
        darkTheme = isDarkTheme ?: isSystemInDarkTheme()
    ) {
        Scaffold(
            topBar = {
                val actionList = listOf(
                    KoreatechBoardAppBarAction(
                        icon = ImageVector.vectorResource(R.drawable.ic_open_in_browser),
                        action = {
                            if (articleUrl != "") {
                                val builder = CustomTabsIntent.Builder()
                                val customTabsIntent = builder.build()
                                customTabsIntent.launchUrl(context, Uri.parse(articleUrl))
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
                    canGoBack = true,
                    backAction = {
                        context.findActivity().finish()
                    },
                    actionList = actionList
                )
            }
        ) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                networkViewModel.collectSideEffect {
                    networkViewModel.handleSideEffect(it)
                }
                val networkState by networkViewModel.collectAsState()
                val isNetworkConnected = networkState.isConnected
                if (isNetworkConnected) {
                    ArticleScreen(
                        articleViewModel = articleViewModel,
                        department = department,
                        uuid = uuid,
                        isDarkTheme = isDarkTheme ?: isSystemInDarkTheme()
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(id = R.string.network_unavailable))
                    }
                }
            }
        }
    }
}
