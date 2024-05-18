package com.kongjak.koreatechboard.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.ui.settings.deptList
import com.kongjak.koreatechboard.util.routes.Department
import koreatech_board.app.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    onArticleClick: (Uuid, String) -> Unit
) {
    homeViewModel.collectSideEffect {
        homeViewModel.handleSideEffect(it)
    }
    val isNetworkConnected = true
    if (isNetworkConnected) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            val homeUiState by homeViewModel.collectAsState()
            val selectedDepartmentIndex = homeUiState.department
            BoardInHome(department = Department.School, onArticleClick = onArticleClick)
            BoardInHome(department = Department.Dorm, onArticleClick = onArticleClick)

            val selectedDepartment = deptList[selectedDepartmentIndex]
            BoardInHome(department = selectedDepartment, onArticleClick = onArticleClick)
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(Res.string.network_unavailable))
        }
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun BoardInHome(
    department: Department,
    onArticleClick: (Uuid, String) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        department.boards.size
    }

    val coroutineScope = rememberCoroutineScope()

    val tabIndex = pagerState.currentPage

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = stringResource(department.stringResource), fontSize = 16.sp)
            }

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
                ArticleList(department, page, onArticleClick)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ArticleList(
    department: Department,
    page: Int,
    onArticleClick: (Uuid, String) -> Unit
) {
    val key by remember { mutableStateOf(department.boards[page].board) }

    val homeBoardViewModel: HomeBoardViewModel = koinViewModel(key = department.name)

    homeBoardViewModel.collectSideEffect {
        homeBoardViewModel.handleSideEffect(it)
    }

    LaunchedEffect(key1 = department.name, key2 = key) {
        homeBoardViewModel.getApi(department.name, key)
    }

    val uiState by homeBoardViewModel.collectAsState()
    val isSuccess = uiState.boardData[key]?.isSuccess ?: false
    val isLoaded = uiState.boardData[key]?.isLoaded ?: false
    val boardData = uiState.boardData[key]?.boardData ?: emptyList()
    val statusCode = uiState.boardData[key]?.statusCode ?: 0

    val coroutineScope = rememberCoroutineScope()

    if (!isLoaded) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (isSuccess && statusCode == 200) {
            if (boardData.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(Res.string.error_no_article))
                }
            } else {
                Column {
                    boardData.forEach { data ->
                        Box(
                            modifier = Modifier
                                .clickable {
                                    onArticleClick(data.uuid, department.name)
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = data.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
                    }
                }
            }
        } else if (isSuccess && statusCode != 200) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = stringResource(Res.string.error_server_down, statusCode))
                Button(onClick = {
                    coroutineScope.launch {
                        homeBoardViewModel.getApi(department.name, key)
                    }
                }) {
                    Text(text = stringResource(Res.string.error_retry))
                }
            }
        } else if (!isSuccess) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = uiState.boardData[key]?.error ?: "")
                Button(onClick = {
                    coroutineScope.launch {
                        homeBoardViewModel.getApi(department.name, key)
                    }
                }) {
                    Text(text = stringResource(Res.string.error_retry))
                }
            }
        }
    }
}
