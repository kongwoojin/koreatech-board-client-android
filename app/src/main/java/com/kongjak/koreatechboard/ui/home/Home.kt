package com.kongjak.koreatechboard.ui.home

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.network.NetworkViewModel
import com.kongjak.koreatechboard.ui.settings.deptList
import com.kongjak.koreatechboard.util.routes.Department
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel()
) {
    val networkState by networkViewModel.uiState.collectAsState()
    val isNetworkConnected = networkState.isConnected
    if (isNetworkConnected) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            val selectedDepartmentIndex by homeViewModel.department.observeAsState()
            BoardInMain(department = Department.School)
            BoardInMain(department = Department.Dorm)

            val selectedDepartment = deptList[selectedDepartmentIndex ?: 0]
            BoardInMain(department = selectedDepartment)
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.network_unavailable))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoardInMain(
    department: Department,
    homeBoardViewModel: HomeBoardViewModel = hiltViewModel(key = department.name)
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        department.boards.size
    }

    val coroutineScope = rememberCoroutineScope()

    val tabIndex = pagerState.currentPage

    var key by remember {
        mutableStateOf(department.boards[0].board)
    }

    LaunchedEffect(key1 = tabIndex) {
        homeBoardViewModel.getApi(department.name, key)
    }

    val uiState = homeBoardViewModel.uiState.collectAsState()
    val isSuccess = uiState.value.isSuccess
    val isLoaded = uiState.value.isLoaded
    val boardList = uiState.value.boardList
    val statusCode = uiState.value.statusCode

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(text = stringResource(id = department.stringResource), fontSize = 16.sp)
            }

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
                key = department.boards[page].board

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
                        if (boardList[key] == null) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = stringResource(R.string.error_no_data))
                            }
                        } else {
                            Column {
                                boardList[key]!!.forEach { data ->
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                val intent = Intent(context, ArticleActivity::class.java)
                                                intent.putExtra("site", department.name)
                                                intent.putExtra("uuid", data.uuid.toString())
                                                context.startActivity(intent)
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
                            Text(text = stringResource(R.string.error_server_down, statusCode))
                            Button(onClick = {
                                homeBoardViewModel.getApi(department.name, key)
                            }) {
                                Text(text = stringResource(id = R.string.error_retry))
                            }
                        }
                    } else if (!isSuccess) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = uiState.value.error)
                            Button(onClick = {
                                homeBoardViewModel.getApi(department.name, key)
                            }) {
                                Text(text = stringResource(id = R.string.error_retry))
                            }
                        }
                    }
                }
            }
        }
    }
}
