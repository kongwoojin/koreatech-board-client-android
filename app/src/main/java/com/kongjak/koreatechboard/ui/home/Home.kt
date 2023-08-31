package com.kongjak.koreatechboard.ui.home

import android.content.Intent
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.settings.deptList
import com.kongjak.koreatechboard.util.routes.Department

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
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
}

@Composable
fun BoardInMain(department: Department, homeBoardViewModel: HomeBoardViewModel = hiltViewModel(key = department.name)) {
    val context = LocalContext.current

    var key by remember {
        mutableStateOf(department.boards[0].board)
    }
    var tabIndex by remember { mutableIntStateOf(0) }
    val isLoaded by homeBoardViewModel.isLoaded.observeAsState(false)

    LaunchedEffect(key1 = tabIndex) {
        homeBoardViewModel.getApi(department.name, key)
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

            ScrollableTabRow(selectedTabIndex = tabIndex, containerColor = Color.Transparent, edgePadding = 0.dp) {
                department.boards.forEachIndexed { index, board ->
                    Tab(
                        text = { Text(text = stringResource(id = board.stringResource)) },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            key = board.board

                            homeBoardViewModel.getApi(department.name, board.board)
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
                if (homeBoardViewModel.statusCode.value!! == 200) {
                    homeBoardViewModel.boardList[key]!!.value!!.forEach { data ->
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clickable {
                                    val intent = Intent(context, ArticleActivity::class.java)
                                    intent.putExtra("site", department.name)
                                    intent.putExtra("uuid", data.uuid.toString())
                                    context.startActivity(intent)
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
