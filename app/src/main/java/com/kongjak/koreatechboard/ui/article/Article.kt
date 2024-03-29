package com.kongjak.koreatechboard.ui.article

import android.widget.TextView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kongjak.koreatechboard.ui.theme.articleSubText
import com.kongjak.koreatechboard.ui.theme.articleTitle
import com.kongjak.koreatechboard.ui.viewmodel.ThemeViewModel
import com.kongjak.koreatechboard.util.fileText
import com.kongjak.koreatechboard.util.htmlText
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    articleViewModel: ArticleViewModel,
    themeViewModel: ThemeViewModel,
    department: String,
    uuid: UUID
) {
    val uiState by articleViewModel.uiState.collectAsState()

    val isLoading = uiState.isLoading

    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            articleViewModel.getArticleData(department, uuid)
        }
    }

    LaunchedEffect(key1 = isLoading) {
        if (isLoading) return@LaunchedEffect
        pullToRefreshState.endRefresh()
    }

    val data = uiState.article
    val context = LocalContext.current
    val contentTextView = remember { TextView(context) }
    val filesTextView = remember { TextView(context) }

    LaunchedEffect(key1 = Unit) {
        pullToRefreshState.startRefresh()
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            data?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.articleTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = it.writer,
                                style = MaterialTheme.typography.articleSubText
                            )
                            Text(
                                text = it.date,
                                style = MaterialTheme.typography.articleSubText
                            )
                        }
                    }

                    key(themeViewModel.isDarkTheme) {
                        var isDarkTheme = themeViewModel.isDarkTheme.value

                        if (isDarkTheme == null) isDarkTheme = isSystemInDarkTheme()

                        val textColor =
                            if (isDarkTheme == true) {
                                Color(0xFFFFFFFF)
                            } else {
                                Color(0xFF000000)
                            }

                        AndroidView(
                            factory = { contentTextView },
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            update = {
                                it.htmlText = data.content
                                it.textSize = 16F
                                it.autoLinkMask = 0x0f
                                it.setTextColor(textColor.toArgb())
                            }
                        )

                        AndroidView(
                            factory = { filesTextView },
                            modifier = Modifier
                                .padding(16.dp),
                            update = {
                                it.fileText = data.files
                                it.setTextColor(textColor.toArgb())
                            }
                        )
                    }
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }
}
