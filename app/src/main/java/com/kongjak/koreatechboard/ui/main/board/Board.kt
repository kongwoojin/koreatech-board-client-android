package com.kongjak.koreatechboard.ui.main.board

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.article.ArticleActivity
import com.kongjak.koreatechboard.ui.components.dialog.TextFieldDialog
import com.kongjak.koreatechboard.ui.main.settings.deptList
import com.kongjak.koreatechboard.ui.main.settings.fullDeptList
import com.kongjak.koreatechboard.ui.network.NetworkViewModel
import com.kongjak.koreatechboard.ui.search.SearchActivity
import com.kongjak.koreatechboard.ui.theme.boardItemSubText
import com.kongjak.koreatechboard.ui.theme.boardItemTitle
import com.kongjak.koreatechboard.util.routes.Department
import kotlinx.coroutines.launch

@Composable
fun BoardScreen(
    boardInitViewModel: BoardInitViewModel = hiltViewModel(),
    defaultDepartment: Department?, // Default department from MainActivity.
    isOpenedFromNotification: Boolean = false
) {
    val uiState by boardInitViewModel.uiState.collectAsState()
    val initDepartment = uiState.initDepartment
    val userDepartment = uiState.userDepartment
    BottomSheetScaffold(
        defaultDepartment ?: fullDeptList[initDepartment],
        userDepartment,
        isOpenedFromNotification = isOpenedFromNotification
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffold(
    initDepartment: Department = Department.School,
    userDepartment: Int = 0,
    isOpenedFromNotification: Boolean
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val department = remember { mutableStateOf(initDepartment) }

    val scaffoldItemList = listOf(
        Department.School,
        Department.Dorm,
        deptList[userDepartment]
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 64.dp,
        sheetContent = {
            LazyColumn {
                items(scaffoldItemList) {
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
        Board(
            contentPadding = innerPadding,
            department = department.value,
            isOpenedFromNotification
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Board(
    contentPadding: PaddingValues,
    department: Department,
    isOpenedFromNotification: Boolean
) {
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
            BoardContent(
                department = department,
                page = page,
                isOpenedFromNotification = isOpenedFromNotification
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardContent(
    department: Department,
    page: Int,
    isOpenedFromNotification: Boolean,
    networkViewModel: NetworkViewModel = hiltViewModel()
) {
    val boardViewModel =
        hiltViewModel<BoardViewModel>(key = "${department.name}:${department.boards[page].board}")

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(key1 = department.name, key2 = department.boards[page].board) {
        pullToRefreshState.startRefresh()
        boardViewModel.getAPI(department.name, department.boards[page].board)
    }

    val uiState by boardViewModel.uiState.collectAsState()
    val lazyPostList = uiState.boardItemsMap.collectAsLazyPagingItems()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            lazyPostList.refresh()
        }
    }

    LaunchedEffect(key1 = lazyPostList.loadState.refresh is LoadState.Loading) {
        if (lazyPostList.loadState.refresh is LoadState.Loading) return@LaunchedEffect
        pullToRefreshState.endRefresh()
    }

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            SearchFAB(department = department, index = page)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { contentPadding ->
            val networkState by networkViewModel.uiState.collectAsState()
            val isNetworkConnected = networkState.isConnected

            LaunchedEffect(key1 = networkState) {
                if (!isNetworkConnected) {
                    snackbarHostState.showSnackbar(context.getString(R.string.network_unavailable))
                } else if (isNetworkConnected) {
                    lazyPostList.retry()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                if ((lazyPostList.loadState.refresh is LoadState.NotLoading) && lazyPostList.itemCount == 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(id = R.string.error_no_data))
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = contentPadding.calculateTopPadding(),
                            bottom = (64 + 16).dp + contentPadding.calculateBottomPadding(),
                            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
                        ),
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
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
                                                intent.putExtra("department", department.name)
                                                intent.putExtra("uuid", it.uuid.toString())
                                                context.startActivity(intent)
                                            }
                                        ),
                                    title = it.title,
                                    writer = it.writer,
                                    isNew = it.isNew,
                                    date = it.writeDate,
                                    isOpenedFromNotification = isOpenedFromNotification
                                )
                                HorizontalDivider(thickness = 0.5.dp, color = Gray)
                            }
                        }

                        lazyPostList.apply {
                            when (isNetworkConnected) {
                                true -> {
                                    when {
                                        loadState.refresh is LoadState.Error -> {
                                            val errorMessage =
                                                (loadState.refresh as LoadState.Error).error.localizedMessage
                                            item { BoardError(errorMessage ?: "") }
                                        }

                                        loadState.append is LoadState.Error -> {
                                            val errorMessage =
                                                (loadState.append as LoadState.Error).error.localizedMessage
                                            item { BoardError(errorMessage ?: "") }
                                        }
                                    }
                                }

                                false -> item { NetworkUnavailable() }
                            }
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
    )
}

@Composable
fun BoardItem(
    modifier: Modifier,
    title: String,
    writer: String,
    date: String,
    isNew: Boolean,
    isOpenedFromNotification: Boolean = false
) {
    val shouldSetBold = isOpenedFromNotification && isNew

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
            style = if (shouldSetBold) {
                MaterialTheme.typography.boardItemTitle.copy(fontWeight = FontWeight.Bold)
            } else {
                MaterialTheme.typography.boardItemTitle
            }
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
    val (showDetailError, setShowDetailError) = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.error_minimum_message, errorMessage),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Button(onClick = { setShowDetailError(!showDetailError) }) {
                Text(text = stringResource(id = if (showDetailError) R.string.error_hide_detail else R.string.error_show_detail))
            }
        }
        if (showDetailError) {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun NetworkUnavailable() {
    Text(
        text = stringResource(R.string.network_unavailable),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
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
        TextFieldDialog(
            title = stringResource(id = R.string.search_dialog_title),
            onConfirmString = stringResource(id = R.string.search_dialog_search),
            onDismissString = stringResource(id = R.string.search_dialog_cancel),
            label = stringResource(id = R.string.search_dialog_hint),
            onConfirm = {
                showDialog = false
                val intent = Intent(context, SearchActivity::class.java)
                intent.putExtra(
                    "department",
                    department.name
                )
                intent.putExtra(
                    "board",
                    department.boards[index].board
                )
                intent.putExtra("title", searchText)
                context.startActivity(intent)
            },
            onDismiss = {
                showDialog = false
            },
            value = searchText
        ) {
            searchText = it
        }
    }
}
