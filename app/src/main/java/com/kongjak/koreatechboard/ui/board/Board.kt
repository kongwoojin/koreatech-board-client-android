package com.kongjak.koreatechboard.ui.board

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.activity.SearchActivity
import com.kongjak.koreatechboard.util.routes.deptList
import kotlinx.coroutines.launch

@Composable
fun BoardScreen(boardViewModel: BoardViewModel = hiltViewModel()) {
    BottomSheetScaffold(boardViewModel = boardViewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Board(boardViewModel: BoardViewModel, contentPadding: PaddingValues) {
    val dept by boardViewModel.department
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        dept.boards.size
    }

    val coroutineScope = rememberCoroutineScope()
    val tabIndex =
        if (pagerState.currentPage >= dept.boards.size) pagerState.initialPage else pagerState.currentPage
    val context = LocalContext.current

    LaunchedEffect(key1 = dept) {
        pagerState.scrollToPage(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val lazyPostList =
            boardViewModel.getAPI(dept.name, dept.boards[tabIndex].board).collectAsLazyPagingItems()

        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            edgePadding = 0.dp
        ) {
            dept.boards.forEachIndexed { index, board ->
                Tab(
                    text = { Text(text = stringResource(id = board.stringResource)) },
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
            boardViewModel.changeIndex(page)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(lazyPostList.itemCount) { index ->
                    val boardItem = lazyPostList[index]
                    boardItem?.let {
                        BoardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .selectable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    selected = false,
                                    onClick = {
                                        val intent = Intent(context, ArticleActivity::class.java)
                                        intent.putExtra("site", dept.name)
                                        intent.putExtra("uuid", it.uuid.toString())
                                        context.startActivity(intent)
                                    }
                                ),
                            title = it.title, writer = it.writer, date = it.writeDate
                        )
                        Divider(color = Gray, thickness = 0.5.dp)
                    }
                }

                lazyPostList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val errorMessage =
                                (loadState.refresh as LoadState.Error).error.localizedMessage
                            item { BoardError(errorMessage) }
                        }

                        loadState.append is LoadState.Error -> {
                            val errorMessage =
                                (loadState.append as LoadState.Error).error.localizedMessage
                            item { BoardError(errorMessage) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoardItem(modifier: Modifier, title: String, writer: String, date: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.typography.titleMedium.color
        )
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = writer,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.typography.titleSmall.color

            )
            Text(
                text = date,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.typography.titleSmall.color
            )
        }
    }
}

@Composable
fun BoardError(errorMessage: String) {
    Text(
        text = stringResource(R.string.server_down, errorMessage),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffold(
    boardViewModel: BoardViewModel
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 64.dp,
        sheetContent = {
            LazyColumn {
                items(deptList) {
                    Box {
                        Text(
                            text = stringResource(id = it.stringResource),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .clickable {
                                    boardViewModel.changeDept(it)
                                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                                }
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Scaffold(
                floatingActionButton = {
                    SearchFAB(boardViewModel = boardViewModel)
                },
                content = { contentPadding ->
                    Board(boardViewModel = boardViewModel, contentPadding = contentPadding)
                }
            )
        }
    }
}

@Composable
fun SearchFAB(boardViewModel: BoardViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            showDialog = true
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = null
        )
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_dialog_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            label = { Text(text = stringResource(id = R.string.search_dialog_hint)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    if (searchText.length < 3) {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.search_more_letter),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        showDialog = false
                                        val intent = Intent(context, SearchActivity::class.java)
                                        intent.putExtra(
                                            "site",
                                            boardViewModel.department.value.name
                                        )
                                        intent.putExtra(
                                            "board",
                                            boardViewModel.department.value.boards[boardViewModel.tabIndex.intValue].board
                                        )
                                        intent.putExtra("title", searchText)
                                        context.startActivity(intent)
                                    }
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { showDialog = false }) {
                                Text(text = stringResource(id = R.string.search_dialog_cancel))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(onClick = {
                                showDialog = false
                                val intent = Intent(context, SearchActivity::class.java)
                                intent.putExtra(
                                    "site",
                                    boardViewModel.department.value.name
                                )
                                intent.putExtra(
                                    "board",
                                    boardViewModel.department.value.boards[boardViewModel.tabIndex.intValue].board
                                )
                                intent.putExtra("title", searchText)
                                context.startActivity(intent)
                            }) {
                                Text(text = stringResource(id = R.string.search_dialog_search))
                            }
                        }
                    }
                }
            }
        )
    }
}
