package com.kongjak.koreatechboard.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.theme.KoreatechBoardTheme
import com.kongjak.koreatechboard.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val department = intent.getStringExtra("department")!!
        val board = intent.getStringExtra("board")!!
        val title = intent.getStringExtra("title")!!

        if (title.length < 3) {
            Toast.makeText(this, getString(R.string.search_more_letter), Toast.LENGTH_SHORT).show()
            finish()
        }

        setContent {
            val isDynamicColor by themeViewModel.isDynamicTheme.observeAsState(true)
            val isDarkTheme by themeViewModel.isDarkTheme.observeAsState()
            KoreatechBoardTheme(
                dynamicColor = isDynamicColor,
                darkTheme = isDarkTheme ?: isSystemInDarkTheme()
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(department, board, title)
                }
            }
        }
    }
}
