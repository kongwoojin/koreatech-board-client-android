package com.kongjak.koreatechboard.ui.article

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.util.fileText
import com.kongjak.koreatechboard.util.htmlText
import java.util.UUID

@Composable
fun ArticleScreen(articleViewModel: ArticleViewModel = hiltViewModel(), site: String, uuid: UUID) {
    val data by articleViewModel.article.observeAsState()
    val context = LocalContext.current
    val contentTextView = remember { TextView(context) }
    val filesTextView = remember { TextView(context) }

    LaunchedEffect(key1 = Unit) {
        articleViewModel.setSiteData(site)
        articleViewModel.setUUIDData(uuid)
        articleViewModel.getArticleData()
    }

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        val isLoading by articleViewModel.isLoading.observeAsState()

        if (isLoading == true) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            data?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = it.title,
                        fontSize = 18.sp,
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
                                fontSize = MaterialTheme.typography.titleSmall.fontSize
                            )
                            Text(
                                text = it.date,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize
                            )
                        }
                    }
                    AndroidView(
                        factory = { contentTextView },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        update = {
                            it.htmlText = data?.content
                            it.textSize = 16F
                        }
                    )
                    AndroidView(
                        factory = { filesTextView },
                        modifier = Modifier
                            .padding(16.dp),
                        update = {
                            it.fileText = data?.files
                        }
                    )
                }
            }
        }
    }
}
