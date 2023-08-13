package com.kongjak.koreatechboard.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.article.ArticleScreen
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
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
fun ArticleMain(site: String, uuid: UUID) {
    val context = LocalContext.current
    Toolbar(context = context, site = site, uuid = uuid)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(context: Context, site: String, uuid: UUID) {
    KoreatechBoardTheme {
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
                    }
                )
            }
        ) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                ArticleScreen(site = site, uuid = uuid)
            }
        }
    }
}
