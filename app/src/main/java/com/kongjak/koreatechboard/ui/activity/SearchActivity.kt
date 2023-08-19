package com.kongjak.koreatechboard.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kongjak.koreatechboard.ui.search.SearchScreen
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val site = intent.getStringExtra("site")!!
        val board = intent.getStringExtra("board")!!
        val title = intent.getStringExtra("title")!!

        setContent {
            KoreatechBoardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(site, board, title)
                }
            }
        }
    }
}
