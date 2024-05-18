package com.kongjak.koreatechboard.ui.board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.ui.components.dialog.TextFieldDialog
import com.kongjak.koreatechboard.ui.settings.deptList
import com.kongjak.koreatechboard.ui.theme.boardItemSubText
import com.kongjak.koreatechboard.ui.theme.boardItemTitle
import com.kongjak.koreatechboard.util.routes.Department
import koreatech_board.app.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.*

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    initDepartment: Int,
    userDepartment: Int,
    onArticleClick: (UUID, String) -> Unit,
    onSearch: (String, String, String) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val departmentList = listOf(
        Department.School,
        Department.Dorm,
        deptList[userDepartment]
    )

    val department = rememberSaveable { mutableStateOf(departmentList[initDepartment].name) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetPeekHeight = 64.dp,
        sheetContent = {
            LazyColumn {
                items(departmentList) {
                    Box {
                        Text(
                            text = stringResource(it.stringResource),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    department.value = it.name
                                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Board(
            contentPadding = innerPadding,
            department = Department.valueOf(department.value),
            onArticleClick = onArticleClick,
            onSearch = onSearch
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun Board(
    contentPadding: PaddingValues,
    department: Department,
    onArticleClick: (UUID, String) -> Unit,
    onSearch: (String, String, String) -> Unit
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
                    text = { Text(text = stringResource(board.stringResource)) },
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
                onArticleClick = onArticleClick,
                onSearch = onSearch
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BoardContent(
    department: Department,
    page: Int,
    onArticleClick: (UUID, String) -> Unit,
    onSearch: (String, String, String) -> Unit,
) {
    val boardViewModel =
        koinViewModel<BoardViewModel>(key = "${department.name}:${department.boards[page].board}")

    boardViewModel.collectSideEffect { boardViewModel.handleSideEffect(it) }

    val pullToRefreshState = rememberPullToRefreshState()

    val uiState by boardViewModel.collectAsState()
    val lazyPostList = uiState.boardItem.collectAsLazyPagingItems()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            if (uiState.isInitialized) {
                lazyPostList.refresh()
            } else {
                boardViewModel.getAPI(department.name, department.boards[page].board)
            }
        }
    }

    LaunchedEffect(key1 = department.name, key2 = department.boards[page].board) {
        pullToRefreshState.startRefresh()
    }

    LaunchedEffect(key1 = lazyPostList.loadState.refresh, key2 = pullToRefreshState.isRefreshing) {
        if (lazyPostList.loadState.refresh is LoadState.NotLoading || lazyPostList.loadState.refresh is LoadState.Error) {
            pullToRefreshState.endRefresh()
        }
    }

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            SearchFAB(department = department, index = page, snackbarHostState = snackbarHostState, onSearch = onSearch)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                if ((lazyPostList.loadState.refresh is LoadState.NotLoading) && lazyPostList.itemCount == 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(Res.string.error_no_article))
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
                                                onArticleClick(it.uuid, department.name)
                                            }
                                        ),
                                    title = it.title,
                                    writer = it.writer,
                                    isNew = it.isNew,
                                    date = it.writeDate
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    thickness = 0.5.dp,
                                    color = Gray
                                )
                            }
                        }

                        lazyPostList.apply {
                            when {
                                loadState.refresh is LoadState.Error -> {
                                    val errorMessage =
                                        (loadState.refresh as LoadState.Error).error.message
                                    item {
                                        BoardError(
                                            errorMessage
                                                ?: stringResource(Res.string.error_unknown)
                                        )
                                    }
                                }

                                loadState.append is LoadState.Error -> {
                                    val errorMessage =
                                        (loadState.append as LoadState.Error).error.message
                                    item {
                                        BoardError(
                                            errorMessage
                                                ?: stringResource(Res.string.error_unknown)
                                        )
                                    }
                                }
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
    isNew: Boolean
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.boardItemTitle
        )
        Row(modifier = Modifier.padding(top = 4.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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

@OptIn(ExperimentalResourceApi::class)
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
                text = stringResource(Res.string.error_minimum_message, errorMessage),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Button(onClick = { setShowDetailError(!showDetailError) }) {
                Text(text = stringResource(if (showDetailError) Res.string.error_hide_detail else Res.string.error_show_detail))
            }
        }
        if (showDetailError) {
            Text(text = errorMessage)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NetworkUnavailable() {
    Text(
        text = stringResource(Res.string.network_unavailable),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SearchFAB(
    department: Department,
    index: Int,
    snackbarHostState: SnackbarHostState,
    onSearch: (String, String, String) -> Unit
) {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val searchMoreLetterString = stringResource(Res.string.search_more_letter)

    FloatingActionButton(
        shape = RoundedCornerShape(16.dp),
        onClick = {
            showDialog = true
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(Res.string.content_description_search)
        )
    }

    if (showDialog) {
        TextFieldDialog(
            title = stringResource(Res.string.search_dialog_title),
            onConfirmString = stringResource(Res.string.search_dialog_search),
            onDismissString = stringResource(Res.string.search_dialog_cancel),
            label = stringResource(Res.string.search_dialog_hint),
            onConfirm = {
                if (searchText.length < 3) {
                    showDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(searchMoreLetterString)
                    }
                    return@TextFieldDialog
                }
                showDialog = false
                onSearch(
                    department.name,
                    department.boards[index].board,
                    searchText
                )
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
