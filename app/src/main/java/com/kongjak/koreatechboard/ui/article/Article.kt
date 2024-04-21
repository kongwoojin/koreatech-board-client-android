package com.kongjak.koreatechboard.ui.article

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
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.kongjak.koreatechboard.ui.components.FileText
import com.kongjak.koreatechboard.ui.components.WebView
import com.kongjak.koreatechboard.ui.theme.articleSubText
import com.kongjak.koreatechboard.ui.theme.articleTitle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    articleViewModel: ArticleViewModel,
    department: String,
    uuid: UUID
) {
    articleViewModel.collectSideEffect {
        articleViewModel.handleSideEffect(it)
    }
    val uiState by articleViewModel.collectAsState()

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

                    WebView(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        html = it.content,
                    )

                    FileText(
                        modifier = Modifier.padding(16.dp),
                        files = it.files
                    )
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
            indicator = { pullRefreshState ->
                PullToRefreshDefaults.Indicator(
                    state = pullRefreshState,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}
