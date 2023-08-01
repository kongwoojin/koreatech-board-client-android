package com.kongjak.koreatechboard.ui.article

import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kongjak.koreatechboard.ui.viewmodel.ArticleViewModel
import com.kongjak.koreatechboard.util.fileText
import com.kongjak.koreatechboard.util.htmlText
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleScreen(articleViewModel: ArticleViewModel = viewModel(), site: String, uuid: UUID) {
    var refreshCount by remember { mutableStateOf(1) }
    val isRefreshing by articleViewModel.isLoading.observeAsState(false)
    val pullRefreshState =
        rememberPullRefreshState(isRefreshing, {
            articleViewModel.getArticleData()
            refreshCount += 1
        })

    val data by articleViewModel.article.observeAsState()
    val context = LocalContext.current
    val contentTextView = remember { TextView(context) }
    val filesTextView = remember { TextView(context) }

    LaunchedEffect(key1 = refreshCount) {
        articleViewModel.setSiteData(site)
        articleViewModel.setUUIDData(uuid)
        articleViewModel.getArticleData()
    }

    Box(
        modifier = Modifier.pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter
    ) {
        data?.let {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val (title, writer, date, content, files) = createRefs()

                Text(
                    text = it.title,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = it.writer,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(writer) {
                            top.linkTo(title.bottom)
                            end.linkTo(title.end)
                        }
                )
                Text(
                    text = it.date,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(date) {
                            top.linkTo(writer.bottom)
                            end.linkTo(writer.end)
                        }
                )
                AndroidView(
                    factory = { contentTextView },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .constrainAs(content) {
                            top.linkTo(date.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    update = {
                        it.htmlText = data?.content
                        it.textSize = 16F
                    }
                )
                AndroidView(
                    factory = { filesTextView },
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(files) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            top.linkTo(content.bottom)
                        },
                    update = {
                        it.fileText = data?.files
                    }
                )
            }
        }

        PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState)
    }
}
