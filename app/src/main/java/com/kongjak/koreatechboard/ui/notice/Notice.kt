package com.kongjak.koreatechboard.ui.notice

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
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.settings.deptList
import com.kongjak.koreatechboard.ui.theme.boardItemSubText
import com.kongjak.koreatechboard.ui.theme.boardItemTitle
import com.kongjak.koreatechboard.ui.theme.noticeDepartmentText
import com.kongjak.koreatechboard.util.routes.BoardItem
import com.kongjak.koreatechboard.util.routes.Department
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun Notice(
    modifier: Modifier = Modifier,
    onArticleClick: (UUID, String) -> Unit,
    noticeViewModel: NoticeViewModel = koinViewModel()
) {
    noticeViewModel.collectSideEffect {
        noticeViewModel.handleSideEffect(it)
    }

    val uiState by noticeViewModel.collectAsState()

    val checkedList = uiState.selectedDepartment
    val userDepartment = uiState.userDepartment

    LaunchedEffect(key1 = checkedList) {
        noticeViewModel.getAllNotices()
    }

    if (!uiState.isLoaded) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = Modifier.fillParentMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val options = listOf(
                        stringResource(id = Department.School.stringResource),
                        stringResource(id = Department.Dorm.stringResource),
                        stringResource(id = deptList[userDepartment].stringResource)
                    )

                    MultiChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onCheckedChange = {
                                    if (index in checkedList) {
                                        noticeViewModel.removeSelectedDepartment(index)
                                    } else {
                                        noticeViewModel.addSelectedDepartment(index)
                                    }
                                },
                                checked = index in checkedList
                            ) {
                                Text(
                                    text = label,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.articles.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(id = R.string.no_new_notice)
                        )
                    }
                }
            } else {
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
                                            onArticleClick(article.uuid, article.department)
                                            noticeViewModel.updateRead(article.uuid, true)
                                        }
                                    ),
                                department = article.department,
                                board = article.board,
                                title = article.title,
                                writer = article.writer,
                                date = article.date,
                                read = article.read
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun NoticeItem(
    modifier: Modifier,
    department: String,
    board: String,
    title: String,
    writer: String,
    date: String,
    read: Boolean
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(
                id = R.string.department_and_board,
                stringResource(id = Department.valueOf(department).stringResource),
                stringResource(id = BoardItem.valueOf(board).stringResource)
            ),
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
            board = "notice",
            read = false
        )
    }
}
