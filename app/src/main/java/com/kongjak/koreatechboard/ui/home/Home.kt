package com.kongjak.koreatechboard.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.util.routes.Department

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        BoardInMain(department = Department.School)
        BoardInMain(department = Department.Dorm)
        // Third BoardInMain will be user's department,
        // Other will fixed to School and Dormitory
        BoardInMain(department = Department.Cse)
    }
}

@Composable
fun BoardInMain(department: Department, homeViewModel: HomeViewModel = hiltViewModel(key = department.name)) {
    var key by remember {
        mutableStateOf(department.boards[0].board)
    }
    var tabIndex by remember { mutableIntStateOf(0) }
    val isLoaded by homeViewModel.isLoaded.observeAsState(false)

    LaunchedEffect(key1 = tabIndex) {
        homeViewModel.getApi(department.name, key)
    }

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

            TabRow(selectedTabIndex = tabIndex, containerColor = Color.Transparent) {
                department.boards.forEachIndexed { index, board ->
                    Tab(
                        text = { Text(text = stringResource(id = board.stringResource)) },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            key = board.board

                            homeViewModel.getApi(department.name, board.board)
                        }
                    )
                }
            }

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
                if (homeViewModel.statusCode.value!! == 200) {
                    homeViewModel.boardList[key]!!.value!!.forEach { data ->
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clickable {
                                    TODO("Implement article screen")
                                }
                        ) {
                            Text(text = data.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                    }
                } else {
                    Text(text = "Error!")
                }
            }
        }
    }
}