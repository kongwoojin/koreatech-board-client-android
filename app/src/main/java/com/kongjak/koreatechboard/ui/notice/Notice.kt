package com.kongjak.koreatechboard.ui.notice

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.article.ArticleActivity
import com.kongjak.koreatechboard.ui.theme.boardItemSubText
import com.kongjak.koreatechboard.ui.theme.boardItemTitle
import com.kongjak.koreatechboard.ui.theme.noticeDepartmentText
import com.kongjak.koreatechboard.util.routes.Department
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@ExperimentalMaterial3Api
@Composable
fun Notice(
    modifier: Modifier,
    noticeViewModel: NoticeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    noticeViewModel.collectSideEffect {
        noticeViewModel.handleSideEffect(it)
    }

    LaunchedEffect(key1 = Unit) {
        noticeViewModel.getAllNotices()
    }

    val uiState by noticeViewModel.collectAsState()

    if (!uiState.isLoaded) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (uiState.articles.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.no_new_notice)
                )
            }
        } else {
            LazyColumn(modifier = modifier) {
                items(
                    items = uiState.articles,
                    key = { it.uuid }
                ) { article ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                noticeViewModel.deleteNotice(article.uuid)
                            }
                            true
                        }
                    )
                    AnimatedVisibility(
                        visible = true,
                        exit = fadeOut(spring())
                    ) {
                        SwipeToDismissBox(
                            state = dismissState,
                            modifier = Modifier,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {
                                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = Dp(20f)),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = stringResource(id = R.string.content_description_delete)
                                        )
                                    }
                                }
                            }
                        ) {
                            NoticeItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .selectable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() },
                                        selected = false,
                                        onClick = {
                                            val intent =
                                                Intent(context, ArticleActivity::class.java)
                                            intent.putExtra("department", article.department)
                                            intent.putExtra("uuid", article.uuid.toString())
                                            context.startActivity(intent)
                                            noticeViewModel.updateRead(article.uuid, true)
                                        }
                                    ),
                                department = article.department,
                                title = article.title,
                                writer = article.writer,
                                date = article.date,
                                read = article.read
                            )
                        }
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun NoticeItem(
    modifier: Modifier,
    department: String,
    title: String,
    writer: String,
    date: String,
    read: Boolean
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Department.valueOf(department).stringResource),
            style = if (read) {
                MaterialTheme.typography.noticeDepartmentText.copy(
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            } else {
                MaterialTheme.typography.noticeDepartmentText
            }
        )
        Column {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(),
                style = if (read) {
                    MaterialTheme.typography.boardItemTitle.copy(
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                } else {
                    MaterialTheme.typography.boardItemTitle
                }
            )
            Row(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = writer,
                    style = if (read) {
                        MaterialTheme.typography.boardItemSubText.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    } else {
                        MaterialTheme.typography.boardItemSubText
                    }
                )
                Text(
                    text = date,
                    style = if (read) {
                        MaterialTheme.typography.boardItemSubText.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    } else {
                        MaterialTheme.typography.boardItemSubText
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun NoticeItemPreview() {
    Surface {
        NoticeItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = "Title",
            writer = "Writer",
            date = "2021-09-01",
            department = "school",
            read = false
        )
    }
}
