package com.kongjak.koreatechboard.ui.board

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.activity.SearchActivity
import com.kongjak.koreatechboard.ui.theme.boardItemSubText
import com.kongjak.koreatechboard.ui.theme.boardItemTitle
import com.kongjak.koreatechboard.util.routes.Department
import com.kongjak.koreatechboard.util.routes.deptList
import kotlinx.coroutines.launch

@Composable
fun BoardScreen() {
    BottomSheetScaffold()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Board(contentPadding: PaddingValues, department: Department) {
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        department.boards.size
    }

    val coroutineScope = rememberCoroutineScope()
    val tabIndex = pagerState.currentPage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            edgePadding = 0.dp
        ) {
            department.boards.forEachIndexed { index, board ->
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

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            BoardContent(department = department, page = page)
        }
    }
}

@Composable
fun BoardContent(
    department: Department,
    page: Int,
    boardViewModel: BoardViewModel = hiltViewModel()
) {
    val lazyPostList =
        boardViewModel.getAPI(department.name, department.boards[page].board)
            .collectAsLazyPagingItems()

    val pullRefreshState =
        rememberPullRefreshState(lazyPostList.loadState.refresh is LoadState.Loading, { lazyPostList.refresh() })

    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            SearchFAB(department = department, index = page)
        },
        content = { contentPadding ->
            Box(modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = contentPadding.calculateTopPadding(),
                        bottom = (64 + 16).dp + contentPadding.calculateBottomPadding(),
                        start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
                    ),
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
                                            val intent =
                                                Intent(context, ArticleActivity::class.java)
                                            intent.putExtra("site", department.name)
                                            intent.putExtra("uuid", it.uuid.toString())
                                            context.startActivity(intent)
                                        }
                                    ),
                                title = it.title,
                                writer = it.writer,
                                date = it.writeDate
                            )
                            HorizontalDivider(thickness = 0.5.dp, color = Gray)
                        }
                    }

                    lazyPostList.apply {
                        when {
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

                PullRefreshIndicator(
                    refreshing = lazyPostList.loadState.refresh is LoadState.Loading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
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
            style = MaterialTheme.typography.boardItemTitle
        )
        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = writer,
                style = MaterialTheme.typography.boardItemSubText
            )
            Text(
                text = date,
                style = MaterialTheme.typography.boardItemSubText
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
fun BottomSheetScaffold() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val department = remember { mutableStateOf<Department>(Department.School) }

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
                                    department.value = it
                                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                                }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Board(contentPadding = innerPadding, department = department.value)
    }
}

@Composable
fun SearchFAB(department: Department, index: Int) {
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
                        .padding(16.dp)
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
                                    showDialog = false
                                    val intent = Intent(context, SearchActivity::class.java)
                                    intent.putExtra(
                                        "site",
                                        department.name
                                    )
                                    intent.putExtra(
                                        "board",
                                        department.boards[index].board
                                    )
                                    intent.putExtra("title", searchText)
                                    context.startActivity(intent)
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
                                    department.name
                                )
                                intent.putExtra(
                                    "board",
                                    department.boards[index].board
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
