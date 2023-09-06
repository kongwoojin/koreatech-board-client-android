package com.kongjak.koreatechboard.ui.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.article.ArticleScreen
import com.kongjak.koreatechboard.ui.article.ArticleViewModel
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.ui.viewmodel.ThemeViewModel
import com.kongjak.koreatechboard.util.findActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ArticleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = UUID.fromString(intent.getStringExtra("uuid"))
        val site = intent.getStringExtra("site")!!

        setContent {
            ArticleMain(site = site, uuid = uuid)
        }
    }
}

@Composable
fun ArticleMain(articleViewModel: ArticleViewModel = hiltViewModel(), site: String, uuid: UUID) {
    val context = LocalContext.current
    Toolbar(articleViewModel = articleViewModel, context = context, site = site, uuid = uuid)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(articleViewModel: ArticleViewModel, themeViewModel: ThemeViewModel = hiltViewModel(), context: Context, site: String, uuid: UUID) {
    val articleUrl by articleViewModel.url.observeAsState()
    val isDynamicColor by themeViewModel.isDynamicTheme.observeAsState(true)
    val isDarkTheme by themeViewModel.isDarkTheme.observeAsState()

    KoreatechBoardTheme(
        dynamicColor = isDynamicColor,
        darkTheme = isDarkTheme ?: isSystemInDarkTheme()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(id = R.string.app_name))
                    },
                    navigationIcon =
                    {
                        IconButton(onClick = {
                            context.findActivity().finish()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            if (articleUrl != null) {
                                val builder = CustomTabsIntent.Builder()
                                val customTabsIntent = builder.build()
                                customTabsIntent.launchUrl(context, Uri.parse(articleUrl))
                            } else {
                                Toast.makeText(context, context.getString(R.string.open_in_browser_failed), Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_open_in_browser),
                                contentDescription = stringResource(id = R.string.open_in_browser)
                            )
                        }
                    }
                )
            }
        ) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                ArticleScreen(articleViewModel = articleViewModel, themeViewModel = themeViewModel, site = site, uuid = uuid)
            }
        }
    }
}
